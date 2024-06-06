package com.ashfaque.demogeminiroomkotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ashfaque.demogeminiroomkotlin.databinding.ActivityMainBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivityMainBinding

    private var position=0

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val array: MutableList<ChatModel> = mutableListOf(ChatModel("Hi!   How can I help you today?", TypeClass.AI, Utils().getCurrentDateTime(dateFormate)))


        val adapter = MyAdapter(this,array) // Convert array to list and pass it to adapter constructor
        mBinding.recyclerView.adapter = adapter

      // adapter.addItem( ChatModel(markdownText, TypeClass.AI,  Utils().getCurrentDateTime(dateFormate)))
      //  adapter.addItem( ChatModel(markdownText, TypeClass.USER,  Utils().getCurrentDateTime(dateFormate)))

        val generativeModel = GenerativeModel(
            // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts
            modelName = "gemini-1.5-flash",
            apiKey = apiKey1+ apiKey2+ apiKey3
        )

        mBinding.btnSend.setOnClickListener {
            val prompt=mBinding.edPrompt.text.toString()

            if(prompt.isEmpty())
            {
                return@setOnClickListener
            }
            mBinding.tvTyping.visibility= View.VISIBLE
            hideKeyboard()
            mBinding.edPrompt.setText("")

            position= adapter.addItem( ChatModel(prompt, TypeClass.USER,  Utils().getCurrentDateTime(dateFormate)))
            mBinding.recyclerView.scrollToPosition(position)

            GlobalScope.launch {

                try {
                    val response = generativeModel.generateContent(prompt)
                    response.text?.let {
                        Log.d("Ashu", it)

                        withContext(Dispatchers.Main)
                        {
                            position=adapter.addItem( ChatModel(it, TypeClass.AI,  Utils().getCurrentDateTime(dateFormate)))
                            mBinding.recyclerView.scrollToPosition(position)
                            mBinding.tvTyping.visibility= View.GONE
                        }
                    }

                } catch (e: Exception) {
                    Log.e("Ashu", "Server error: ${e.message}")
                    withContext(Dispatchers.Main)
                    {
                        mBinding.tvTyping.visibility= View.GONE
                    }
                }


            }
        }

    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(mBinding.btnSend.windowToken, 0)
    }



}