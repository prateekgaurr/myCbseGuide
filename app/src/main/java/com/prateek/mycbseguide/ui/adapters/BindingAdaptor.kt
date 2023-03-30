package com.prateek.mycbseguide.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.prateek.mycbseguide.R
import com.squareup.picasso.Picasso

class BindingAdaptor {
    companion object{

        @JvmStatic
        @BindingAdapter("load_item_image")
        fun loadItemImage(imageView: ImageView, imageUrl : String?){
            Picasso
                .get()
                .load(imageUrl)
                .error(R.drawable.baseline_error_outline_24)
                .centerCrop()
                .resize(200,200)
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("set_item_name")
        fun setItemName(textView: TextView, itemName: String?){
            textView.text = itemName
        }




    }
}
