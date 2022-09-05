package com.example.asteroidRadarApp.until

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidRadarApp.R
import com.example.asteroidRadarApp.adapter.AsteroidAdapter
import com.example.asteroidRadarApp.model.AsteroidModel
import com.example.asteroidRadarApp.model.PictureOfDayModel
import com.example.asteroidRadarApp.model.State
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("addList")
fun addList(recyclerView: RecyclerView, list: List<AsteroidModel>?) {
    if (list != null) {
        val ad = recyclerView.adapter as AsteroidAdapter
        ad.submitList(list)
    }
}


@BindingAdapter("showImageOfDay")
fun showImageOfDay(img: ImageView, model: PictureOfDayModel?) {
    if (model != null && model.mediaType == "image") Picasso.with(img.context).load(model.url)
        .error(R.drawable.placeholder_picture_of_day).into(img)
    else img.setImageResource(R.drawable.placeholder_picture_of_day)
}

@BindingAdapter("visibilityLoading")
fun visibilityLoading(progressBar: ProgressBar, state: State?) {
    progressBar.visibility = when (state) {
        State.Done -> View.GONE
        else -> View.VISIBLE
    }
}


