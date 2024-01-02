package com.kg.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private var textViewInput : TextView? = null
    private var previousresult : TextView? = null
    private var isdot : Boolean= false
    private var isnumber : Boolean = false
    private lateinit var btnzero: Button
    private lateinit var btnone: Button
    private lateinit var btntwo: Button
    private lateinit var btnthree: Button
    private lateinit var btnfive: Button
    private lateinit var btnsix: Button
    private lateinit var btnseven: Button
    private lateinit var btneight: Button
    private lateinit var btnnine: Button
    private lateinit var btnmul: Button
    private lateinit var btnDel: Button
    private lateinit var btnsub: Button
    private lateinit var btnequal: Button
    private lateinit var btnadd: Button
    private lateinit var btnfour: Button
    private lateinit var btndot: Button
    private lateinit var btndivide: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(android.R.style.Theme_DeviceDefault_Light)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        textViewInput = findViewById(R.id.textView)
        previousresult = findViewById(R.id.previousresult)
        btnone = findViewById(R.id.btnone)
        btntwo = findViewById(R.id.btntwo)
        btnthree = findViewById(R.id.btnthree)
        btnfour = findViewById(R.id.btnfour)
        btnfive = findViewById(R.id.btnfive)
        btnsix = findViewById(R.id.btnsix)
        btnseven = findViewById(R.id.btnseven)
        btneight = findViewById(R.id.btneight)
        btnnine = findViewById(R.id.btnnine)
        btnzero = findViewById(R.id.btnzero)
        btnadd = findViewById(R.id.btnadd)
        btnsub = findViewById(R.id.btnsub)
        btndivide = findViewById(R.id.btndivide)
        btnmul = findViewById(R.id.btnmul)
        btndot = findViewById(R.id.btndot)
        btnDel = findViewById(R.id.btnDel)
        btnequal = findViewById(R.id.btnequal)

        btnone.setOnClickListener {
            onDigit(it)
        }
        btntwo.setOnClickListener {
            onDigit(it)
        }
        btnthree.setOnClickListener {
            onDigit(it)
        }
        btnfour.setOnClickListener {
            onDigit(it)
        }
        btnfive.setOnClickListener {
            onDigit(it)
        }
        btnsix.setOnClickListener {
            onDigit(it)
        }
        btnseven.setOnClickListener {
            onDigit(it)
        }
        btneight.setOnClickListener {
            onDigit(it)
        }
        btnnine.setOnClickListener {
            onDigit(it)
        }
        btnzero.setOnClickListener {
            onDigit(it)
        }
        btnequal.setOnClickListener {
            onEqual()
        }
        btnDel.setOnClickListener {
            onClear()
        }
        btndot.setOnClickListener {
            onDot()
        }

        btnadd.setOnClickListener {
            onOperate(it)
        }

        btnmul.setOnClickListener {
            onOperate(it)
        }

        btnsub.setOnClickListener {
            onOperate(it)
        }
        btndivide.setOnClickListener {
            onOperate(it)
        }
    }

    private fun onDigit(view : View)
    {
        if(previousresult?.text == textViewInput?.text || textViewInput?.text.toString().contains("Infinity")) {
            textViewInput?.text = ""
        }
        textViewInput?.text = checkZero(textViewInput?.text.toString())
        textViewInput?.append((view as Button).text)
        isnumber = true
    }

    private fun checkZero(value : String) : String
    {
        if(value.contains("0") && value.length == 1)
        {
            return value.substringAfter("0")
        }
        return value
    }

    private fun onClear()
    {
        textViewInput?.text = ""
        isnumber = false
        isdot = false
    }

    private fun onDot()
    {
            if(isnumber && !isdot && !(textViewInput?.text.toString().contains("Infinity") ||
                textViewInput?.text.toString().contains("NaN")))
            {
                textViewInput?.append(".")
                isnumber = false
                isdot = true
            }
    }
    private fun checkOperatornumber() : Boolean
    {
        return !(textViewInput?.text.toString().endsWith("+-") || textViewInput?.text.toString().endsWith("--")
                || textViewInput?.text.toString().endsWith("*-") || textViewInput?.text.toString().endsWith("/-"))
    }
    private fun onOperate(view : View) { // işlem sonunda en fazla iki operatör koyulmalı
        textViewInput?.text?.let {
            if (it.toString() == "Infinity" || it.toString() == "NaN" || it.toString() == "-Infinity" ) {
                Toast.makeText(
                    this,
                    "You can't make an operation",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if((!textViewInput?.text.toString().endsWith('.')) &&  ((view as Button).text == "-" && checkOperatornumber() ) && !(it.contains('-') && it.length == 1))
            {
                textViewInput?.append(view.text)
            }
            else if(!textViewInput?.text.toString().endsWith('.') && ((view as Button).text == "+" || view.text == "*"
                    || view.text == "/"  || view.text == "-" ) && isnumber)
            {
                textViewInput?.append(view.text)
            }
            isnumber = false
            isdot = false
        }
    }

    private fun removeDot(value : Double) : String
    {
        var number = value.toString()
        if(number.endsWith(".0"))
        {
            number = number.substringBefore(".")
        }
        return number
    }

    private fun modify(list : MutableList<Any> , index : Int , char : Char)
    {
        val result = when (char) {
            '*' -> (list[index - 1] as Double) * (list[index + 1] as Double)
            '/' -> (list[index - 1] as Double) / (list[index + 1] as Double)
            '+' -> (list[index - 1] as Double) + (list[index + 1] as Double)
            '-' -> (list[index - 1] as Double) - (list[index + 1] as Double)
            else -> throw IllegalArgumentException("Invalid operator: $char")
        }
        list.removeAt(index - 1)
        list.removeAt(index - 1)
        list.removeAt(index - 1)
        list.add(index - 1, result)
    }

    private fun firstOperation(value: List<Any>) {
        val list = value.toMutableList() // Create a copy of the input list
        while (list.size != 1) {
            /// index of listede o eleman yoksa -1 döndürür
            if (list.contains('*') && list.indexOf('*') < list.indexOf('/')) {
                modify(list,list.indexOf('*'), '*')

            } else if (list.contains('/') && list.indexOf('/') < list.indexOf('*')) {
                modify(list,list.indexOf('/'), '/')

            }
            else if(list.contains('*'))
            {
                modify(list,list.indexOf('*'), '*')

            }
            else if(list.contains('/'))
            {
                modify(list,list.indexOf('/'), '/')

            }
            else if (list.contains('+')) {
                modify(list,list.indexOf('+'), '+')

            } else if (list.contains('-')) {
                modify(list,list.indexOf('-'), '-')

            }
        }
        textViewInput?.text = removeDot(list[0] as Double)
        previousresult?.text = textViewInput?.text
        isdot = textViewInput?.text.toString().contains('.')
        isnumber = true
    }

    private fun onEqual() {
        if (isnumber) {
            var a = 2
            val list = mutableListOf<Any>()
            var number = ""
            var i = 0
            var flag = false
            val value = textViewInput?.text.toString()
            if (value.isNotEmpty() && value[i] == '-')
                flag = true
            while (i < value.length) {
                if (value[i].isDigit() || (value[i] == '.' && number.isNotEmpty())) {
                    number += value[i]
                } else if (value[i] == '-' || value[i] == '+' || value[i] == '/' || value[i] == '*') {
                    if (i != 0 && !value[i - 1].isDigit() && value[i] == '-')
                        flag = true
                    if (number.isNotEmpty()) {
                        if (!flag)
                            list.add(number.toDouble())
                        else {
                            list.removeAt(list.size - 1)
                            list.add(number.toDouble() * (-1).toDouble())
                            flag = false
                        }
                        number = ""
                        if (list.size >= 2 && list[a - 1] == '-' && (list[a] as Double) > 0) {
                            list.removeAt(a - 1)
                            list.add(a - 1, '+')
                            list.add(a, list.removeAt(a) as Double * (-1).toDouble())
                            a++
                        }
                    }
                    list.add(value[i])
                }
                i++
            }
            if (number.isNotEmpty() && !flag)
                list.add(number.toDouble())
            else if (number.isNotEmpty()) {
                list.removeAt(list.size - 1)
                list.add(number.toDouble() * (-1).toDouble())
            }
            firstOperation(list)
        }
    }
}
