package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.models.ExtendedIngredient
import com.example.foody.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foody.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredient_row_layout.view.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_row_layout, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ingridient_imageView.load(BASE_IMAGE_URL + ingredientList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error)
        }
        holder.itemView.ingridient_title_textView.text =
            ingredientList[position].name.replaceFirstChar {
                it.uppercase()
            }
        holder.itemView.ingredient_unit.text = ingredientList[position].unit
        holder.itemView.consistency_textView.text = ingredientList[position].consistency
        holder.itemView.ingredient_original_textView.text = ingredientList[position].original
        holder.itemView.ingredient_amount.text = ingredientList[position].amount.toString()
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}