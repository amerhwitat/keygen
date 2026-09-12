package com.amerhwitat.keygen.mobile
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
class MainActivity:Activity(){override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);val r=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;gravity=Gravity.CENTER;setPadding(32,32,32,32)};val t=TextView(this).apply{text="Keygen — Kotlin Mobile";textSize=24f;gravity=Gravity.CENTER};val s=TextView(this).apply{text="Authorized key generation only\nEd25519/trusted-node research\n128D runtime: ready";textSize=16f;gravity=Gravity.CENTER;setPadding(0,24,0,24)};val b=Button(this).apply{text="Initialize generator";setOnClickListener{s.text="Generator boundary: active\nTrusted-node identity: ready\n128D runtime: active"}};r.addView(t);r.addView(s);r.addView(b);setContentView(r)}}
