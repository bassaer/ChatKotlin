package com.github.bassaer.chatkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.github.bassaer.chatmessageview.models.Message
import com.github.bassaer.chatmessageview.models.User
import com.github.bassaer.chatmessageview.utils.ChatBot
import com.github.bassaer.chatmessageview.views.ChatView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mChatView : ChatView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //user id
        val myId : Int = 0
        val yourId : Int = 1
        //user icon
        val myIcon : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.face_1)
        val yourIcon : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.face_2)
        //user name
        val myName : String = "Michael"
        val yourName : String = "Emily"
        //Create User
        val me : User = User(myId, myName, myIcon)
        val you : User = User(yourId, yourName, yourIcon)

        mChatView = findViewById(R.id.chat_view) as ChatView

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500))
        mChatView.setLeftBubbleColor(Color.WHITE)
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500))
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900))
        mChatView.setSendIcon(R.drawable.ic_action_send)
        mChatView.setRightMessageTextColor(Color.WHITE)
        mChatView.setLeftMessageTextColor(Color.BLACK)
        mChatView.setUsernameTextColor(Color.WHITE)
        mChatView.setSendTimeTextColor(Color.WHITE)
        mChatView.setDateSeparatorColor(Color.WHITE)
        mChatView.setInputTextHint("new message...")
        mChatView.setMessageMarginTop(5)
        mChatView.setMessageMarginBottom(5)

        mChatView.setOnClickSendButtonListener {
            val message : Message = Message.Builder()
                    .setUser(me)
                    .setMessageText(mChatView.inputText)
                    .hideIcon(true)
                    .setRightMessage(true)
                    .build()
            mChatView.send(message)
            //Clear text box
            mChatView.inputText = ""

            val receivedMessage = Message.Builder()
                    .setRightMessage(false)
                    .setUser(you)
                    .setMessageText(ChatBot.talk(me.name, message.messageText))
                    .build()
            //Reply time
            val delay : Int = (Random().nextInt(4) + 1) * 1000
            Handler().postDelayed({ mChatView.receive(receivedMessage) }, delay.toLong())
        }
    }
}
