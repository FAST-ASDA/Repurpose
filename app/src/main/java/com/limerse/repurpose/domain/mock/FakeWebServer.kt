package com.limerse.repurpose.domain.mock

import com.limerse.repurpose.R
import com.limerse.repurpose.model.CenterRepository
import com.limerse.repurpose.model.entities.Product
import com.limerse.repurpose.model.entities.ProductCategoryModel
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class FakeWebServer {

    fun addCategory() {
        val listOfCategory = ArrayList<ProductCategoryModel>()
        listOfCategory
            .add(
                ProductCategoryModel(
                    "Latest Arrivals",
                    "Grad the latest deals on the Zara collection!",
                    "40%",
                    R.drawable.girls
                )
            )
        listOfCategory
            .add(
                ProductCategoryModel(
                    "Thrifting Specials",
                    "Connect with your neighbouring friends and thrift!",
                    "25%",
                    R.drawable.hanger
                )
            )
        listOfCategory
            .add(
                ProductCategoryModel(
                    "Social Connections",
                    "Follow the trendy thrifters in your area!",
                    "35%",
                    R.drawable.family
                )
            )
        CenterRepository.centerRepository!!.listOfCategory = listOfCategory
    }

    // Vaccum Cleaner
    private val allElectronics: Unit
        get() {
            val productMap = ConcurrentHashMap<String, MutableList<Product?>>()
            val tvList = mutableListOf<Product?>()

            // TV
            tvList.add(
                Product(
                    "PUMA Jacket",
                    "Bought it just 2 months ago",
                    "Enjoy this amazing jacket from puma which gives an athletic look for you.",
                    "16000",
                    "12",
                    "13990",
                    "0",
                    "https://m.media-amazon.com/images/I/512Z2SJds9L._UL1280_.jpg",
                    "tv_1"
                )
            )
            tvList.add(
                Product(
                    "Zara Top",
                    "Clip Dot Peasant Crop Top",
                    "Had this amazing piece of art for a month",
                    "17000",
                    "12",
                    "13990",
                    "0",
                    "https://forever21.imgix.net/img/app/product/6/605358-5688950.jpg?w=573&auto=format",
                    "tv_2"
                )
            )
            tvList.add(
                Product(
                    "Bata Shoes",
                    "Hush Puppies Men's Hpo2 Flex Formal Shoes",
                    "Lovely pair, really comfortable",
                    "18000",
                    "12",
                    "13990",
                    "0",
                    "https://m.media-amazon.com/images/I/91Qo4nTsttL._UL1500_.jpg",
                    "tv_3"
                )
            )
            tvList.add(
                Product(
                    "Gucci Bag",
                    "GG Marmont small matelassé leather shoulder bag",
                    "Lucious Bag, loved it!",
                    "100800",
                    "12",
                    "88704",
                    "0",
                    "https://cdn-images.farfetch-contents.com/12/96/49/36/12964936_31615675_1000.jpg",
                    "tv_4"
                )
            )
            tvList.add(
                Product(
                    "Apple iWatch",
                    "Gold Aluminium Case with Pink Sand Sport Band",
                    "Works great and in amazing condition!",
                    "50000",
                    "20",
                    "49000",
                    "0",
                    "https://m.media-amazon.com/images/I/81UNELrDc7L._SL1500_.jpg",
                    "tv_5"
                )
            )
            productMap["Jackets"] = tvList

            CenterRepository.centerRepository!!.setMapOfProductsInCategory(productMap)
        }

    // Chair
    val allFurnitures: Unit
        get() {
            val productMap = ConcurrentHashMap<String, MutableList<Product?>>()
            var productlist = mutableListOf<Product?>()

            // Table
            productlist
                .add(
                    Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img6a.flixcart.com/image/coffee-table/q/f/4/ct15bl-mdf-royal-oak-dark-400x400-imaeehkd8xuheh2u.jpeg",
                        "table_1"
                    )
                )
            productlist
                .add(
                    Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img5a.flixcart.com/image/coffee-table/c/z/e/afr1096-sm-mango-wood-onlineshoppee-brown-400x400-imaea6c2bhwz8tns.jpeg",
                        "table_2"
                    )
                )
            productlist
                .add(
                    Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img5a.flixcart.com/image/coffee-table/u/n/p/brass-table0016-rosewood-sheesham-zameerwazeer-beige-400x400-imaedwk5ksph9ut2.jpeg",
                        "table_3"
                    )
                )
            productlist
                .add(
                    Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img6a.flixcart.com/image/coffee-table/v/h/h/side-tb-53-ad-particle-board-debono-acacia-dark-matt-400x400-imaecnctfgjahsnu.jpeg",
                        "table_4"
                    )
                )
            productlist
                .add(
                    Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img5a.flixcart.com/image/coffee-table/c/z/e/afr1096-sm-mango-wood-onlineshoppee-brown-400x400-imaea6c2bhwz8tns.jpeg",
                        "table_5"
                    )
                )
            productlist
                .add(
                    Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img5a.flixcart.com/image/coffee-table/k/y/h/1-particle-board-wood-an-wood-coffee-400x400-imae7uvzqsf4ynan.jpeg",
                        "table_6"
                    )
                )
            productMap["Tables"] = productlist
            productlist = ArrayList()

            // Chair
            productlist
                .add(
                    Product(
                        "Bean Bag Chair Cover",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img5a.flixcart.com/image/bean-bag/5/b/b/boss-moda-chair-br1088-comf-on-xxxl-400x400-imae9k78vg8gjh3q.jpeg",
                        "chair_1"
                    )
                )
            productlist
                .add(
                    Product(
                        "Bean Bag Chair Cover",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img6a.flixcart.com/image/office-study-chair/e/f/p/flversaossblu-stainless-steel-nilkamal-400x400-imaeeptqczc5kbjg.jpeg",
                        "chair_2"
                    )
                )
            productlist
                .add(
                    Product(
                        "Bean Bag Chair Cover",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img5a.flixcart.com/image/bean-bag/7/w/b/chr-01-fab-homez-xxxl-400x400-imae9qnbfwr9vkk4.jpeg",
                        "chair_3"
                    )
                )
            productlist
                .add(
                    Product(
                        "Adiko Leatherette Office Chair",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img5a.flixcart.com/image/office-study-chair/h/z/d/adxn275-pu-leatherette-adiko-400x400-imaedpmyhzefdzgz.jpeg",
                        "chair_4"
                    )
                )
            productlist
                .add(
                    Product(
                        "Adiko Leatherette Office Chair",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img5a.flixcart.com/image/office-study-chair/h/z/d/adxn275-pu-leatherette-adiko-400x400-imaedpmyytefgvz7.jpeg",
                        "chair_5"
                    )
                )
            productlist
                .add(
                    Product(
                        "Adiko Leatherette Office Chair",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img6a.flixcart.com/image/office-study-chair/j/y/q/adpn-d021-pp-adiko-400x400-imaee2vrg9bkkxjg.jpeg",
                        "chair_6"
                    )
                )
            productlist
                .add(
                    Product(
                        "Adiko Leatherette Office Chair",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img6a.flixcart.com/image/office-study-chair/k/s/2/adxn-034-pu-leatherette-adiko-400x400-imaedpmyyyg8bdmv.jpeg",
                        "chair_7"
                    )
                )
            productlist
                .add(
                    Product(
                        "Adiko Leatherette Office Chair",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img6a.flixcart.com/image/bean-bag/t/8/n/fk0100391-star-xxxl-400x400-imae72dsb5h2r9uj.jpeg",
                        "chair_8"
                    )
                )
            productlist
                .add(
                    Product(
                        "Adiko Leatherette Office Chair",
                        "ab Homez XXXL Bean Bag Chair Cover (Without Filling)",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "36500",
                        "20",
                        "1200",
                        "0",
                        "http://img5a.flixcart.com/image/bean-bag/3/h/w/rydclassicgreenl-rockyard-large-400x400-imae6zfaz6qzj3jd.jpeg",
                        "chair_9"
                    )
                )
            productMap["Chairs"] = productlist
            productlist = ArrayList()

            // Chair
            productlist
                .add(
                    Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img5a.flixcart.com/image/collapsible-wardrobe/h/h/g/best-quality-3-5-feet-foldable-storage-cabinet-almirah-shelf-400x400-imaees5fq7wbndak.jpeg",
                        "almirah_1"
                    )
                )
            productlist
                .add(
                    Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img6a.flixcart.com/image/collapsible-wardrobe/d/n/s/cb265-carbon-steel-cbeeso-dark-beige-400x400-imaefn9vha2hm9qk.jpeg",
                        "almirah_2"
                    )
                )
            productlist
                .add(
                    Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img6a.flixcart.com/image/wardrobe-closet/b/v/3/srw-146-jute-pindia-blue-400x400-imaeb9g4y6tegzfn.jpeg",
                        "almirah_3"
                    )
                )
            productlist
                .add(
                    Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img6a.flixcart.com/image/cupboard-almirah/y/w/q/100009052-particle-board-housefull-mahogany-400x400-imaebazkwhv64p8q.jpeg",
                        "almirah_4"
                    )
                )
            productlist
                .add(
                    Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img5a.flixcart.com/image/collapsible-wardrobe/w/c/k/srw-116a-aluminium-pindia-maroon-wardrobe-400x400-imaeb9g4945dqunu.jpeg",
                        "almirah_5"
                    )
                )
            productlist
                .add(
                    Product(
                        "Metal Free Standing Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img6a.flixcart.com/image/wardrobe-closet/f/b/p/srw-167-jute-pindia-purple-400x400-imaeb9g4d8uvatck.jpeg",
                        "almirah_6"
                    )
                )
            productMap["Almirah"] = productlist
            CenterRepository.centerRepository!!.setMapOfProductsInCategory(productMap)
        }

    fun getAllProducts(productCategory: Int) {
        when (productCategory) {
            0 -> {
                allElectronics
            }
            else -> {
                allFurnitures
            }
        }
    }

    companion object {
        private var fakeServer: FakeWebServer? = null
        val fakeWebServer: FakeWebServer?
            get() {
                if (null == fakeServer) {
                    fakeServer = FakeWebServer()
                }
                return fakeServer
            }
    }
}