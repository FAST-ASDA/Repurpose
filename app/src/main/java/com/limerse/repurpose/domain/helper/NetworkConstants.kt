
package com.limerse.repurpose.domain.helper

// TODO: Auto-generated Javadoc
/**
 * The Interface NetworkConstants.
 */
interface NetworkConstants {
    companion object {
        /**
         * The Constant URL_BASE_URI.
         */
        // public static final String URL_BASE_URI =
        // "http://192.168.2.7:8080/Delivery2Home/";
        const val URL_BASE_URI = ("http://delivery2home.com"
                + "/Delivery2Home/")

        /**
         * The Constant URL_GET_PRODUCTS_BY_CATEGORY.
         */
        const val URL_GET_ALL_CATEGORY = (URL_BASE_URI
                + "categories")

        /**
         * The Constant URL_GET_PRODUCTS_BY_CATEGORY.
         */
        const val URL_GET_PRODUCTS_MAP = (URL_BASE_URI
                + "productMap")

        /**
         * The Constant URL_GET_PRODUCTS_BY_CATEGORY.
         */
        const val URL_PLACE_ORDER = URL_BASE_URI + "insertOrder"
        const val URL_APPLY_COUPAN = (URL_BASE_URI
                + "validateCoupan")
        // -------------------------
        // Products functionality
        /**
         * The Constant getProductByCategory.
         */
        // -------------------------
        const val GET_ALL_PRODUCT_BY_CATEGORY = 0
        const val GET_ALL_PRODUCT = 1
        const val GET_SHOPPING_LIST = 9
        const val DEBUGABLE = true
    }
}