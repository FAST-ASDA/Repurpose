package com.limerse.repurpose.model

import com.limerse.repurpose.model.entities.Product
import com.limerse.repurpose.model.entities.ProductCategoryModel
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class CenterRepository {
    var listOfCategory = ArrayList<ProductCategoryModel>()
    var mapOfProductsInCategory = ConcurrentHashMap<String, MutableList<Product?>>()
    var listOfProductsInShoppingList = Collections.synchronizedList(ArrayList<Product>())
    private val listOfItemSetsForDataMining: MutableList<Set<String>> = ArrayList()
    fun setListOfProductsInShoppingList(getShoppingList: ArrayList<Product>) {
        listOfProductsInShoppingList = getShoppingList
    }

    fun getMapOfProductsInCategory(): Map<String, MutableList<Product?>> {
        return mapOfProductsInCategory
    }

    @JvmName("setMapOfProductsInCategory1")
    fun setMapOfProductsInCategory(mapOfProductsInCategory: ConcurrentHashMap<String, MutableList<Product?>>) {
        this.mapOfProductsInCategory = mapOfProductsInCategory
    }

    val itemSetList: List<Set<String>>
        get() = listOfItemSetsForDataMining

    fun addToItemSetList(list: Set<String>) {
        listOfItemSetsForDataMining.add(list)
    }

    companion object {
        @JvmStatic
        var centerRepository: CenterRepository? = null
            get() {
                when (field) {
                    null -> {
                        field = CenterRepository()
                    }
                }
                return field
            }
            private set
    }
}