
package com.limerse.repurpose.domain.helper

import android.util.Log
import com.limerse.repurpose.model.CenterRepository
import com.limerse.repurpose.model.entities.Product
import com.limerse.repurpose.model.entities.ProductCategoryModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class JSONParser
/**
 * @param networktaskType
 * @param jsonResponse
 */(private val networktaskType: Int, private val jsonResponse: String?) {
    fun parse() {
        if (jsonResponse != null) {
            try {
                when (networktaskType) {
                    NetworkConstants.GET_ALL_PRODUCT_BY_CATEGORY -> {
                        if (NetworkConstants.DEBUGABLE) Log.e(
                            "Parse: ",
                            "GetAllCategoryTask $jsonResponse"
                        )
                        val categoryArray = JSONArray(jsonResponse)
                        CenterRepository.centerRepository!!.listOfCategory
                            .clear()
                        var i = 0
                        while (i < categoryArray.length()) {
                            CenterRepository.centerRepository!!.listOfCategory
                                .add(
                                    ProductCategoryModel(
                                        categoryArray
                                            .getJSONObject(i).getString(
                                                "categoryName"
                                            ), categoryArray
                                            .getJSONObject(i).getString(
                                                "productDescription"
                                            ),
                                        categoryArray.getJSONObject(i)
                                            .getString("productDiscount"),
                                        categoryArray.getJSONObject(i)
                                            .getString("productUrl")
                                    )
                                )
                            i++
                        }
                    }
                    NetworkConstants.Companion.GET_ALL_PRODUCT -> {
                        if (NetworkConstants.Companion.DEBUGABLE) Log.e(
                            "Parse: ", "GetAllProductFromCategoryTask "
                                    + jsonResponse
                        )
                        val productMapObject = JSONObject(jsonResponse)
                        CenterRepository.centerRepository!!.mapOfProductsInCategory
                            .clear()
                        var categoryCount = 0
                        while (categoryCount < CenterRepository
                                .centerRepository!!.listOfCategory.size
                        ) {
                            val tempProductList = mutableListOf<Product?>()

                            // get json array for stored category
                            if (productMapObject.optJSONArray(
                                    CenterRepository.centerRepository!!.listOfCategory[categoryCount].productCategoryName
                                ) != null
                            ) {
                                val productListWithCategory = productMapObject
                                    .getJSONArray(
                                        CenterRepository.centerRepository!!.listOfCategory[categoryCount]
                                            .productCategoryName
                                    )
                                println(
                                    CenterRepository.centerRepository!!.listOfCategory[categoryCount]
                                        .productCategoryName
                                )

                                // ArrayList<Product> tempProductList = new
                                // ArrayList<Product>();
                                var i = 0
                                while (i < productListWithCategory
                                        .length()
                                ) {


                                    // get all product in that category
                                    val productListObjecty = productListWithCategory
                                        .getJSONObject(i)
                                    if (productListObjecty.length() != 0) {
                                        tempProductList.add(
                                            Product(
                                                productListObjecty
                                                    .getString("productName"),
                                                productListObjecty
                                                    .getString("description"),
                                                productListObjecty
                                                    .getString("longDescription"),
                                                productListObjecty
                                                    .getString("mrp"),
                                                productListObjecty
                                                    .getString("discount"),
                                                productListObjecty
                                                    .getString("salePrice"),  /*
                                             * productListObjecty
											 *
											 * .getString("avbleQuantity"),
											 */
                                                "0", productListObjecty
                                                    .getString("imageUrl"),
                                                productListObjecty
                                                    .getString("productId")
                                            )
                                        )
                                        Log.d(
                                            "Parse:GetAllProduct",
                                            "tempProductList$tempProductList"
                                        )
                                    }
                                    i++
                                }
                            }
                            CenterRepository
                                .centerRepository!!.mapOfProductsInCategory[categoryCount.toString()] = tempProductList
                            categoryCount++
                        }
                    }
                    NetworkConstants.Companion.GET_SHOPPING_LIST -> {
                        if (NetworkConstants.Companion.DEBUGABLE) Log.e(
                            "Parse: ", "GetAllProductFromCategoryTask "
                                    + jsonResponse
                        )

                        // JSONObject productMapObject = new
                        // JSONObject(jsonResponse);
                        CenterRepository.centerRepository!!.listOfProductsInShoppingList
                            .clear()
                        val mycartArray = JSONArray(jsonResponse)

                        //
                        // for (int categoryCount = 0; categoryCount <
                        // CenterRepository
                        // .getCenterRepository().getListOfCategory().size();
                        // categoryCount++) {
                        //
                        val tempProductList = ArrayList<Product>()

                        // get json array for stored category

                        // if (productMapObject.optJSONArray(CenterRepository
                        // .getCenterRepository().getListOfCategory()
                        // .get(categoryCount).getProductCategoryName()) != null) {
                        //

                        // JSONArray productListWithCategory = productMapObject
                        // .getJSONArray(CenterRepository
                        // .getCenterRepository()
                        // .getListOfCategory()
                        // .get(categoryCount)
                        // .getProductCategoryName());
                        //
                        // System.out.println(CenterRepository
                        // .getCenterRepository().getListOfCategory()
                        // .get(categoryCount)
                        // .getProductCategoryName());

                        // ArrayList<Product> tempProductList = new
                        // ArrayList<Product>();
                        var i = 0
                        while (i < mycartArray.length()) {


                            // get all product in that category
                            val productListObjecty = mycartArray
                                .getJSONObject(i)
                            if (productListObjecty.length() != 0) {
                                CenterRepository.centerRepository!!.listOfProductsInShoppingList.add(
                                        Product(
                                            productListObjecty
                                                .getString("productName"), productListObjecty
                                                .getString("description"), productListObjecty
                                                .getString("longDescription"), productListObjecty
                                                .getString("mrp"), productListObjecty
                                                .getString("discount"), productListObjecty
                                                .getString("salePrice"),  /*
                             * productListObjecty
							 *
							 * .getString("avbleQuantity"),
							 */
                                            "0", productListObjecty
                                                .getString("imageUrl"), productListObjecty
                                                .getString("productId")
                                        )
                                    )
                                Log.d(
                                    "GetAllProduct",
                                    "tempProductList$tempProductList"
                                )
                            }
                            i++
                        }
                    }
                    else -> {
                    }
                }

                // TODO parse JSON acc to request
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url")
        }
    }

    companion object {
        @Throws(JSONException::class, IllegalAccessException::class)
        fun toJSON(`object`: Any): JSONObject {
            val c: Class<*> = `object`.javaClass
            val jsonObject = JSONObject()
            for (field in c.declaredFields) {
                field.isAccessible = true
                val name = field.name
                val value = field[`object`].toString()
                jsonObject.put(name, value)
            }
            println(jsonObject.toString())
            return jsonObject
        }

        // public static String toJSON(Object object) throws JSONException,
        // IllegalAccessException {
        // String str = "";
        // Class c = object.getClass();
        // JSONObject jsonObject = new JSONObject();
        // for (Field field : c.getDeclaredFields()) {
        // field.setAccessible(true);
        // String name = field.getName();
        // String value = String.valueOf(field.get(object));
        // jsonObject.put(name, value);
        // }
        // System.out.println(jsonObject.toString());
        //
        // return jsonObject.toString();
        // }
        //
        // public static String toJSON(List list) throws JSONException,
        // IllegalAccessException {
        // JSONArray jsonArray = new JSONArray();
        // for (Object i : list) {
        // String jstr = toJSON(i);
        // JSONObject jsonObject = new JSONObject(jstr);
        // jsonArray.put(jsonObject);
        // }
        // return jsonArray.toString();
        // }
        @Throws(JSONException::class, IllegalAccessException::class)
        fun toJSONFromList(list: List<*>): JSONArray {
            val jsonArray = JSONArray()
            for (i in list) {
                val jstr = toJSON(i!!)
                // JSONObject jsonObject = new JSONObject(jstr);
                jsonArray.put(jstr)
            }
            return jsonArray
        }
    }
}