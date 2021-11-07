package com.limerse.repurpose.model.entities

class Product(
    itemName: String, itemShortDesc: String, itemDetail: String,
    MRP: String, discount: String, sellMRP: String, quantity: String,
    imageURL: String, orderId: String
) {
    /**
     * The item short desc.
     */
    var itemShortDesc = ""

    /**
     * The item detail.
     */
    var itemDetail = ""

    /**
     * The mrp.
     */
    var mRP: String

    /**
     * The discount.
     */
    var discountNumeric: String
        private set

    /**
     * The sell mrp.
     */
    var sellMRP: String

    /**
     * The quantity.
     */
    var quantity: String

    /**
     * The image url.
     */
    var imageURL = ""

    /**
     * The item name.
     */
    var itemName = ""
    var productId = ""
    fun getDiscount(): String {
        return discountNumeric + "%"
    }

    fun setDiscount(discount: String) {
        discountNumeric = discount
    }

    /**
     * @param itemName
     * @param itemShortDesc
     * @param itemDetail
     * @param MRP
     * @param discount
     * @param sellMRP
     * @param quantity
     * @param imageURL
     */
    init {
        this.itemName = itemName
        this.itemShortDesc = itemShortDesc
        this.itemDetail = itemDetail
        mRP = MRP
        discountNumeric = discount
        this.sellMRP = sellMRP
        this.quantity = quantity
        this.imageURL = imageURL
        productId = orderId
    }
}