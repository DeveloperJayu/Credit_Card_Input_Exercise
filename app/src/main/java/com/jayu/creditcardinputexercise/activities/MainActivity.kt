@file:Suppress("DEPRECATION")

package com.jayu.creditcardinputexercise.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jayu.creditcardinputexercise.R
import com.jayu.creditcardinputexercise.utils.LuhnCheck
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var txtCardNumber : TextInputEditText
    private lateinit var txtExpiry : TextInputEditText
    private lateinit var txtSecurityCode : TextInputEditText
    private lateinit var txtFName : TextInputEditText
    private lateinit var txtLName : TextInputEditText
    private lateinit var btnSubmit : Button
    private lateinit var btnReset : Button
    private lateinit var tnlCardNumber : TextInputLayout
    private lateinit var tnlExpiry : TextInputLayout
    private lateinit var tnlSecurityCode : TextInputLayout
    private lateinit var tnlFName : TextInputLayout
    private lateinit var tnlLName : TextInputLayout
    private lateinit var txtCardError : TextView
    private lateinit var txtExpiryError : TextView
    private lateinit var txtSecurityError : TextView
    private lateinit var txtFNameError : TextView
    private lateinit var txtLNameError : TextView
    private lateinit var cardType : String
    private var cardSupported = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtCardNumber = findViewById(R.id.txtCardNumber)
        txtExpiry = findViewById(R.id.txtExpiry)
        txtSecurityCode = findViewById(R.id.txtSecurityCode)
        txtFName = findViewById(R.id.txtFName)
        txtLName = findViewById(R.id.txtSName)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
        tnlCardNumber = findViewById(R.id.tnl_CardNumber)
        tnlExpiry = findViewById(R.id.tnl_Expiry)
        tnlSecurityCode = findViewById(R.id.tnl_SecurityCode)
        tnlFName = findViewById(R.id.tnl_fName)
        tnlLName = findViewById(R.id.tnl_sName)
        txtCardError = findViewById(R.id.txtCardError)
        txtExpiryError = findViewById(R.id.txtExpiryError)
        txtSecurityError = findViewById(R.id.txtSecurityError)
        txtFNameError = findViewById(R.id.txtFNameError)
        txtLNameError = findViewById(R.id.txtLNameError)

        btnSubmit.setOnClickListener {

            val cardNumber = txtCardNumber.text.toString().trim()
            val expiry = txtExpiry.text.toString().trim()
            val cvv = txtSecurityCode.text.toString().trim()
            val fName = txtFName.text.toString().trim()
            val sName = txtLName.text.toString().trim()

            setDefault() //Set the view as default

            val validator = validatorClass(cardNumber, expiry, cvv, fName, sName) //Checking the details

            if (validator){
                //Showing the alert dialog
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage(getString(R.string.paymentSuccess))
                alertDialog.setPositiveButton(getString(R.string.ok)){_,_ ->

                }
                alertDialog.show()
            }
        }

        btnReset.setOnClickListener {
            txtCardNumber.setText("")
            txtExpiry.setText("")
            txtSecurityCode.setText("")
            txtFName.setText("")
            txtLName.setText("")
            txtCardNumber.requestFocus()
            setDefault()
        }
    }

    private fun validatorClass(
        cardNumber: String,
        expiry: String,
        cvv: String,
        fName: String,
        lName: String
    ) : Boolean {

        //Checking whether any field is empty or not
        when {
            cardNumber.isEmpty() -> {
                txtCardError.visibility = View.VISIBLE
                txtCardError.text = getString(R.string.cardNumberBlankError)
                tnlCardNumber.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlCardNumber.setHintTextAppearance(R.style.error_appearance)
                txtCardNumber.requestFocus()
                return false
            }
            expiry.isEmpty() -> {
                txtExpiryError.visibility = View.VISIBLE
                txtExpiryError.text = getString(R.string.expiryBlankError)
                tnlExpiry.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlExpiry.setHintTextAppearance(R.style.error_appearance)
                txtExpiry.requestFocus()
                return false
            }
            cvv.isEmpty() -> {
                txtSecurityError.visibility = View.VISIBLE
                txtSecurityError.text = getString(R.string.securityCodeBlankError)
                tnlSecurityCode.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlSecurityCode.setHintTextAppearance(R.style.error_appearance)
                txtSecurityCode.requestFocus()
                return false
            }
            fName.isEmpty() -> {
                txtFNameError.visibility = View.VISIBLE
                txtFNameError.text = getString(R.string.fNameBlankError)
                tnlFName.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlFName.setHintTextAppearance(R.style.error_appearance)
                txtFName.requestFocus()
                return false
            }
            lName.isEmpty() -> {
                txtLNameError.visibility = View.VISIBLE
                txtLNameError.text = getString(R.string.lNameBlankError)
                tnlLName.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlLName.setHintTextAppearance(R.style.error_appearance)
                txtLName.requestFocus()
                return false
            }
        }

        cardType = getCardType(cardNumber)

        val validCard = checkCard(cardNumber, cardType)
        val validCVV = checkCVV(cardType, cvv)
        val validExpiry = checkExpiry(expiry)

        //second level of checking
        when {
            !cardSupported -> {
                txtCardError.visibility = View.VISIBLE
                txtCardError.text = getString(R.string.cardNotSupportedError)
                tnlCardNumber.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlCardNumber.setHintTextAppearance(R.style.error_appearance)
                txtCardNumber.requestFocus()
                return false
            }
            !validCard -> {
                txtCardError.visibility = View.VISIBLE
                txtCardError.text = getString(R.string.cardNumberDigitError)
                tnlCardNumber.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlCardNumber.setHintTextAppearance(R.style.error_appearance)
                txtCardNumber.requestFocus()
                return false
            }
            !LuhnCheck().isValid(cardNumber) -> {
                txtCardError.visibility = View.VISIBLE
                txtCardError.text = getString(R.string.cardNumberDigitError)
                tnlCardNumber.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlCardNumber.setHintTextAppearance(R.style.error_appearance)
                txtCardNumber.requestFocus()
                return false
            }
            !expiry.matches("\\d{2}/\\d{2}".toRegex()) -> {
                txtExpiryError.visibility = View.VISIBLE
                txtExpiryError.text = getString(R.string.expiryFormatError)
                tnlExpiry.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlExpiry.setHintTextAppearance(R.style.error_appearance)
                txtExpiry.requestFocus()
                return false
            }
            !validExpiry -> {
                txtExpiryError.visibility = View.VISIBLE
                txtExpiryError.text = getString(R.string.cardExpiredError)
                tnlExpiry.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlExpiry.setHintTextAppearance(R.style.error_appearance)
                txtExpiry.requestFocus()
                return false
            }
            !validCVV -> {
                txtSecurityError.visibility = View.VISIBLE
                txtSecurityError.text = getString(R.string.securityCodeLengthError)
                tnlSecurityCode.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlSecurityCode.setHintTextAppearance(R.style.error_appearance)
                txtSecurityCode.requestFocus()
                return false
            }
            !fName.matches("[ A-Za-z]+$".toRegex()) -> {
                txtFNameError.visibility = View.VISIBLE
                txtFNameError.text = getString(R.string.fNameStringError)
                tnlFName.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlFName.setHintTextAppearance(R.style.error_appearance)
                txtFName.requestFocus()
                return false
            }
            !lName.matches("[ A-Za-z]+$".toRegex()) -> {
                txtLNameError.visibility = View.VISIBLE
                txtLNameError.text = getString(R.string.lNameStringError)
                tnlLName.boxStrokeColor = resources.getColor(R.color.colorRed)
                tnlLName.setHintTextAppearance(R.style.error_appearance)
                txtLName.requestFocus()
                return false
            }
            else -> return true
        }
    }

    private fun setDefault() {
        txtCardError.visibility = View.GONE
        txtExpiryError.visibility = View.GONE
        txtSecurityError.visibility = View.GONE
        txtFNameError.visibility = View.GONE
        txtLNameError.visibility = View.GONE
        tnlCardNumber.boxStrokeColor = resources.getColor(R.color.colorPrimary)
        tnlExpiry.boxStrokeColor = resources.getColor(R.color.colorPrimary)
        tnlSecurityCode.boxStrokeColor = resources.getColor(R.color.colorPrimary)
        tnlFName.boxStrokeColor = resources.getColor(R.color.colorPrimary)
        tnlLName.boxStrokeColor = resources.getColor(R.color.colorPrimary)
        tnlCardNumber.setHintTextAppearance(R.style.normal_appearance)
        tnlSecurityCode.setHintTextAppearance(R.style.normal_appearance)
        tnlExpiry.setHintTextAppearance(R.style.normal_appearance)
        tnlFName.setHintTextAppearance(R.style.normal_appearance)
        tnlLName.setHintTextAppearance(R.style.normal_appearance)
    }

    @SuppressLint("SimpleDateFormat")
    private fun checkExpiry(expiry: String): Boolean {
        val array = expiry.split("/".toRegex()).toTypedArray()
        val mm = array[0]
        val yy = array[1]
        val eDate = "$mm/$yy"
        val myFormat = SimpleDateFormat("MM/yy")
        val d = Date()
        val today = myFormat.format(d.time)
        val currentDate = myFormat.parse(today)
        val expiryDate = myFormat.parse(eDate)
        val difference =expiryDate!!.time - currentDate!!.time
        return difference >= 0
    }

    private fun checkCVV(cardType: String, cvv: String): Boolean {
        return if (cardType == getString(R.string.visa) || cardType == getString(R.string.master) || cardType == getString(
                R.string.discover
            )){
            cvv.length == 3
        }
        else !(cardType == getString(R.string.american) && cvv.length != 4)
    }

    private fun getCardType(cardNumber: String) : String {
        return when {
            cardNumber[0].toString() == "3" -> {
                getString(R.string.american)
            }
            cardNumber[0].toString() == "4" -> {
                getString(R.string.visa)
            }
            cardNumber[0].toString() == "5" -> {
                getString(R.string.master)
            }
            cardNumber[0].toString() == "6" -> {
                getString(R.string.discover)
            }
            else -> {
                cardSupported = false
                return ""
            }
        }
    }

    private fun checkCard(cardNumber: String, cardType: String): Boolean {

        return when {
            cardType == getString(R.string.visa) && cardNumber.length == 16 -> {
                true
            }
            cardType == getString(R.string.master) && cardNumber.length ==16 -> {
                true
            }
            cardType == getString(R.string.master) && cardNumber.length ==16 -> {
                true
            }
            cardType == getString(R.string.american) && cardNumber.length ==15 -> {
                true
            }
            else -> false
        }

    }
}