package com.limerse.repurpose.domain.mock

import com.limerse.repurpose.model.CenterRepository
import com.limerse.repurpose.model.entities.Product
import com.limerse.repurpose.model.entities.ProductCategoryModel
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/*
 * This class serve as fake server and provides dummy product and category with real Image Urls taken from flipkart
 */
class FakeWebServer {
    fun initiateFakeServer() {
        addCategory()
    }

    fun addCategory() {
        val listOfCategory = ArrayList<ProductCategoryModel>()
        listOfCategory
            .add(
                ProductCategoryModel(
                    "Fashion",
                    "Fashion Items",
                    "10%",
                    "https://images.unsplash.com/photo-1541701494587-cb58502866ab?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1740&q=80"
                )
            )
        listOfCategory
            .add(
                ProductCategoryModel(
                    "Influences",
                    "Influencers",
                    "15%",
                    "https://images.unsplash.com/photo-1541701494587-cb58502866ab?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1740&q=80"
                )
            )
        CenterRepository.centerRepository!!.listOfCategory = listOfCategory
    }
    // Ovens

    // TV

    // Vaccum Cleaner
    val allElectronics: Unit
        get() {
            val productMap = ConcurrentHashMap<String, MutableList<Product?>>()
            var productlist = mutableListOf<Product?>()

            // Ovens
            productlist
                .add(
                    Product(
                        "Solo Microwave Oven",
                        "IFB 17PMMEC1 17 L Solo Microwave Oven",
                        "Explore the joys of cooking with IFB 17PM MEC1 Solo Microwave Oven. The budget-friendly appliance has several nifty features including Multi Power Levels and Speed Defrost to make cooking a fun-filled experience.",
                        "5490",
                        "10",
                        "4290",
                        "0",
                        "http://img6a.flixcart.com/image/microwave-new/3/3/z/ifb-17pmmec1-400x400-imae4g4uzzjsumhk.jpeg",
                        "oven_1"
                    )
                )
            productlist
                .add(
                    Product(
                        "Solo Microwave Oven",
                        "Bajaj 1701MT 17 L Solo Microwave Oven",
                        "Explore the joys of cooking with IFB 17PM MEC1 Solo Microwave Oven. The budget-friendly appliance has several nifty features including Multi Power Levels and Speed Defrost to make cooking a fun-filled experience.",
                        "5000",
                        "10",
                        "4290",
                        "0",
                        "http://img6a.flixcart.com/image/microwave-new/z/j/p/bajaj-1701mt-400x400-imae4ty4vyzhaagz.jpeg",
                        "oven_2"
                    )
                )
            productlist
                .add(
                    Product(
                        "Solo Microwave Oven",
                        "Whirlpool MW 25 BG 25 L Grill Microwave Oven",
                        "http://img6a.flixcart.com/image/microwave-new/a/y/f/whirlpool-mw-25-bg-400x400-imaebagzstnngjqt.jpeg",
                        "5290",
                        "10",
                        "4290",
                        "0",
                        "http://img6a.flixcart.com/image/microwave-new/z/j/p/bajaj-1701mt-400x400-imae4ty4vyzhaagz.jpeg",
                        "oven_3"
                    )
                )
            productlist
                .add(
                    Product(
                        "Solo Microwave Oven",
                        "Morphy Richards 25CG 25 L Convection Microwave Oven",
                        "http://img5a.flixcart.com/image/microwave-new/v/q/y/morphy-richard-25cg-400x400-imadxecx93kb6q4f.jpeg",
                        "5300",
                        "12",
                        "4290",
                        "0",
                        "http://img6a.flixcart.com/image/microwave-new/z/j/p/bajaj-1701mt-400x400-imae4ty4vyzhaagz.jpeg",
                        "oven_4"
                    )
                )
            productlist
                .add(
                    Product(
                        "Solo Microwave Oven",
                        "IFB 25SC4 25 L Convection Microwave Oven",
                        "http://img5a.flixcart.com/image/microwave-new/v/q/y/morphy-richard-25cg-400x400-imadxecx93kb6q4f.jpeg",
                        "5190",
                        "10",
                        "4290",
                        "0",
                        "http://img6a.flixcart.com/image/microwave-new/y/k/m/ifb-25sc4-400x400-imaef2pztynvqjaf.jpeg",
                        "oven_5"
                    )
                )
            productMap["Microwave oven"] = productlist
            val tvList = mutableListOf<Product?>()

            // TV
            tvList.add(
                Product(
                    "LED",
                    "Vu 80cm (32) HD Ready LED TV",
                    "Enjoy movie night with the family on this 80cm LED TV from Vu. With an A+ grade panel, this TV renders crisp details that make what you're watching look realistic.",
                    "16000",
                    "12",
                    "13990",
                    "0",
                    "http://img5a.flixcart.com/image/television/g/y/w/vu-32k160mrevd-400x400-imae93ahpwtchzys.jpeg",
                    "tv_1"
                )
            )
            tvList.add(
                Product(
                    "LED 1",
                    "Vu 80cm (32) HD Ready LED TV",
                    "Enjoy movie night with the family on this 80cm LED TV from Vu. With an A+ grade panel, this TV renders crisp details that make what you're watching look realistic.",
                    "17000",
                    "12",
                    "13990",
                    "0",
                    "http://img6a.flixcart.com/image/television/z/f/w/bpl-bpl080d51h-400x400-imaeeztqvhxbnam2.jpeg",
                    "tv_2"
                )
            )
            tvList.add(
                Product(
                    "LED 2",
                    "Vu 80cm (32) HD Ready LED TV",
                    "Enjoy movie night with the family on this 80cm LED TV from Vu. With an A+ grade panel, this TV renders crisp details that make what you're watching look realistic.",
                    "18000",
                    "12",
                    "13990",
                    "0",
                    "http://img6a.flixcart.com/image/television/f/b/z/micromax-43x6300mhd-400x400-imaednxv8bgznhbx.jpeg",
                    "tv_3"
                )
            )
            tvList.add(
                Product(
                    "LED 3",
                    "Vu 80cm (32) HD Ready LED TV",
                    "Enjoy movie night with the family on this 80cm LED TV from Vu. With an A+ grade panel, this TV renders crisp details that make what you're watching look realistic.",
                    "16000",
                    "12",
                    "13990",
                    "0",
                    "http://img6a.flixcart.com/image/television/a/w/z/vu-32d6545-400x400-imaebagzbpzqhmxc.jpeg",
                    "tv_4"
                )
            )
            tvList.add(
                Product(
                    "LED 4",
                    "Vu 80cm (32) HD Ready LED TV",
                    "Enjoy movie night with the family on this 80cm LED TV from Vu. With an A+ grade panel, this TV renders crisp details that make what you're watching look realistic.",
                    "16000",
                    "12",
                    "13990",
                    "0",
                    "http://img6a.flixcart.com/image/television/s/r/t/lg-32lf550a-400x400-imae8nyvxyjds3qu.jpeg",
                    "tv_5"
                )
            )
            productMap["Television"] = tvList
            productlist = ArrayList()

            // Vaccum Cleaner
            productlist
                .add(
                    Product(
                        "Easy Clean Plus Hand-held ",
                        "Eureka Forbes Easy Clean Plus Hand-held Vacuum Cleaner",
                        "The Eureka Forbes Easy Clean vacuum cleaner is best for those who are looking for a machine that makes cleaning easier and is convenient to use. It is a compact and powerful machine with high suction and low power consumption.",
                        "2699",
                        "10",
                        "2566",
                        "0",
                        "http://img5a.flixcart.com/image/vacuum-cleaner/e/e/g/eureka-forbes-easy-clean-easy-clean-plus-400x400-imae7dam5ey3vaeb.jpeg",
                        "v_cleaner_1"
                    )
                )
            productlist
                .add(
                    Product(
                        "Easy Clean Plus Hand-held ",
                        "Eureka Forbes Easy Clean Plus Hand-held Vacuum Cleaner",
                        "The Eureka Forbes Easy Clean vacuum cleaner is best for those who are looking for a machine that makes cleaning easier and is convenient to use. It is a compact and powerful machine with high suction and low power consumption.",
                        "2699",
                        "10",
                        "2566",
                        "0",
                        "http://img6a.flixcart.com/image/vacuum-cleaner/j/e/x/nova-vc-761h-plus-vacuum-cleaner-400x400-imaecmhyadgxzzpg.jpeg",
                        "v_cleaner_2"
                    )
                )
            productlist
                .add(
                    Product(
                        "Easy Clean Plus Hand-held ",
                        "Eureka Forbes Easy Clean Plus Hand-held Vacuum Cleaner",
                        "The Eureka Forbes Easy Clean vacuum cleaner is best for those who are looking for a machine that makes cleaning easier and is convenient to use. It is a compact and powerful machine with high suction and low power consumption.",
                        "2699",
                        "10",
                        "2566",
                        "0",
                        "http://img6a.flixcart.com/image/vacuum-cleaner/y/g/b/eureka-forbes-car-clean-car-clean-400x400-imae376v2kta5utj.jpeg",
                        "v_cleaner_3"
                    )
                )
            productlist
                .add(
                    Product(
                        "Easy Clean Plus Hand-held ",
                        "Eureka Forbes Easy Clean Plus Hand-held Vacuum Cleaner",
                        "The Eureka Forbes Easy Clean vacuum cleaner is best for those who are looking for a machine that makes cleaning easier and is convenient to use. It is a compact and powerful machine with high suction and low power consumption.",
                        "2699",
                        "10",
                        "2566",
                        "0",
                        "http://img5a.flixcart.com/image/vacuum-cleaner/m/y/g/sarita-115-400x400-imae9b5zhzjagykx.jpeg",
                        "v_cleaner_4"
                    )
                )
            productlist
                .add(
                    Product(
                        "Easy Clean Plus Hand-held ",
                        "Eureka Forbes Easy Clean Plus Hand-held Vacuum Cleaner",
                        "The Eureka Forbes Easy Clean vacuum cleaner is best for those who are looking for a machine that makes cleaning easier and is convenient to use. It is a compact and powerful machine with high suction and low power consumption.",
                        "2699",
                        "10",
                        "2566",
                        "0",
                        "http://img6a.flixcart.com/image/vacuum-cleaner/s/c/j/eureka-forbes-trendy-steel-trendy-steel-400x400-imae7vashkfj2hgk.jpeg",
                        "v_cleaner_5"
                    )
                )
            productMap["Vaccum Cleaner"] = productlist
            CenterRepository.centerRepository!!.setMapOfProductsInCategory(productMap)
        }
    // Table

    // Chair

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
            productMap["Almirah"] = productlist
            CenterRepository.centerRepository!!.setMapOfProductsInCategory(productMap)
        }

    fun getAllProducts(productCategory: Int) {
        if (productCategory == 0) {
            allElectronics
        } else {
            allFurnitures
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