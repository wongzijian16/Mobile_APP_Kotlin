package com.example.naturemagnet.datagenerator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.naturemagnet.R
import com.example.naturemagnet.entity.*


class SampleDataGenerator {

    companion object {
        fun getCustomers(): List<Customer>{

            return listOf(
                Customer("jiahie","jiahielow@gmail.com","0153134124","123asd","jalanMalaysia"),
                Customer("kotlin","kotlin@gmail.com","0153123124","123asd","kotlinMalaysia"),
                Customer("wenxin","wenxin1@gmail.com","0153134124","123asd","Jalan Kuala Lumpur 1"),
                Customer("jingchong","jingchong1@gmail.com","0153134124","123asd","Jalan Kuala Lumpur 2")
            )
        }

        //generate activities data
        fun injectActivities(context: Context): List<Activity> {
            val listOfImage = listOf(R.drawable.sustainable_cities_development,R.drawable.beach_cleaning,R.drawable.charity_fundraising)
            val bitmapList : MutableList<Bitmap> = mutableListOf()
            var bitmap : Bitmap

            for(image in listOfImage){
                bitmap = BitmapFactory.decodeResource(context.resources,image)
                bitmapList.add(bitmap)
            }
            return listOf(
                Activity(
                    "Sustainable cities development techniques",
                    "Techniques for Sustainable Development",
                    "2022/10/04 20:59:00",
                    "In this event, there will be several industry researchers presenting their founding in how to effectively establish a cities development plan that are sustainable in both for the human and the environment of the cities",
                    "2022/09/31 24:00:00",
                    "2022/09/27 13:39:21",
                    "Pavillion @ Bukit Jalil",
                    bitmapList[0],
                    82,
                    2,
                    1
                ),
                Activity(
                    "Beach Cleaning @ the rimbayu",
                    "Environment Preservation",
                    "2022/11/02 12:35:00",
                    "We will be in cooperation with the local authority to clean the beach located @ the Rimbayu area and aim to recycle all the collected material to improve the quality of the environment.",
                    "2022/10/15 12:00:00",
                    "2022/09/27 14:28:44",
                    "Rimbayu @ Bukit Indah",
                    bitmapList[1],
                    2914,
                    1,
                    2
                ),
                Activity(
                    "Charity fund raising",
                    "Charity Fund Raising",
                    "2022/12/12 19:21:00",
                    "All kind of item will be accepted as a charity goods including tangible & intangible product that could be utilized by the old folks home",
                    "2022/11/15 24:00:00",
                    "2022/09/27 17:21:51",
                    "Chow Kit",
                    bitmapList[2],
                    27,
                    2,
                    1
                )
            )
        }

        fun injectCategories(context: Context): List<Category> {
            var icon: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.photo)

            return listOf(
                Category(
                    "Environmental",
                    "Environmental category include activities that focuses on either preserving & conservation the environment; Preserving refers to action to protects the environment from harmful human activities while Conservation refers to the practice of paving the way for protecting the environment and natural resources regardless of entity size",
                    icon
                ),
                Category(
                    "Charity",
                    "Charity is refers those activities that are primarily aimed for philanthropy and social well-being",
                    icon
                )
            )
        }


//        fun getProductCategory(): List<ProductCategory>{
//
//            return listOf(
//                ProductCategory("Bathroom"),
//                ProductCategory("Fashion"),
//                ProductCategory("Others")
//            )
//        }

        fun getProducts(context:Context): List<Product>{
            val listOfImage = listOf(R.mipmap.towel,R.mipmap.yoga_mat,R.mipmap.toothbrush,R.mipmap.bag, R.mipmap.wallet, R.mipmap.steel_bottle)
            val bitmapList : MutableList<Bitmap> = mutableListOf()
            var bitmap : Bitmap


            for(image in listOfImage){
                bitmap = BitmapFactory.decodeResource(context.resources,image)
                bitmapList.add(bitmap)
            }


            return listOf(
                Product("Wool Blanket", "RM 30", "55% recycled wool, 30% recycled polyester, 15% recycled other fibres. For every blanket sold, we donate RM15 to the Asylum Seeker Resource Centre", 20,  bitmapList[0], 1),
                Product("Yoga Mat", "RM 40", "95% Recycled Mat. For every yoga mat sold, we donate RM20 to the Asylum Seeker Resource Centre", 20,  bitmapList[1], 2),
                Product("Toothbrush", "RM 25", "99% recycled Toothbrush. For every toothbrush sold, we donate RM10 to the Asylum Seeker Resource Centre", 40,  bitmapList[2], 1),
                Product("Tote Bag", "RM 30", "85% recycled Tote Bag. For every toothbrush sold, we donate RM15 to the Asylum Seeker Resource Centre", 30,  bitmapList[3], 3),
                Product("Wallet", "RM 30", "80% recycled Wallet. For every wallet sold, we donate RM15 to the Asylum Seeker Resource Centre", 35,  bitmapList[4], 3),
                Product("Steel Bottle", "RM 50", "80% recycled Steel Bottle. For every bottle sold, we donate RM25 to the Asylum Seeker Resource Centre", 20,  bitmapList[5], 2)
            )
        }


//        fun getOrders(): List<Order> {
//            return listOf(
//                //order status: pending, confirmed, packaging, delivered
//                Order(
//                    "3 Jun 2022 11:05:30",
//                    "7 Jun 2022 09:30",
//                    "Packaging",
//                    30.00,
//                    "1293847263",
//                    "23, Jalan Kepong Baru",
//                    "0191234567",
//                    "null",
//                    1
//                ),
//                Order(
//                    "10 Jun 2022 11:05:30",
//                    "null",
//                    "Pending",
//                    80.00,
//                    "1234567890",
//                    "Shah Alam",
//                    "0191234567",
//                    "null",
//                    2
//                ),
//                Order(
//                    "20 Jun 2022 11:05:30",
//                    "30 Jun 2022 10:45",
//                    "Packaging",
//                    50.00,
//                    "4736472834",
//                    "Setapak Central",
//                    "0123274598",
//                    "null",
//                    2
//                ),
//                Order(
//                    "30 Jun 2022 11:05:30",
//                    "2 July 2022 15:30",
//                    "Confirmed",
//                    90.00,
//                    "9483746274",
//                    "56, Jalan Utama, Kepong Ulu",
//                    "0165973845",
//                    "null",
//                    1
//                ),
//                Order(
//                    "1 July 2022 11:05:30",
//                    "8 July 2022 12:30",
//                    "Delivered",
//                    60.00,
//                    "8495738475",
//                    "99, Jalan Mewah, Bukit Jalil",
//                    "0194863669",
//                    "8 July 2022 12:35",
//                    2
//                ),
//            )
//        }
//
//        fun getOrderItems() : List<OrderItem> {
//            return listOf(
//                OrderItem(1,1,1),
//                OrderItem(2,2,2),
//                OrderItem(3,3,2),
//                OrderItem(4,4,3),
//                OrderItem(5,5,1)
//            )
//        }
        fun injectNews() : List<News>{
            return listOf(
                News("‘This must stop’: Albanese hints at climate change talks with King Charles III", "http://www.climate-news-db.com/article/4427", "Sky News Australia", "2022-09-18"),
                News("Most candidates prepared to spend ratepayer money on climate change initiatives", "http://www.climate-news-db.com/article/5101", "Stuff.co.nz", "2022-09-18"),
                News("China lost its Yangtze River dolphin. Climate change is coming for other species next", "http://www.climate-news-db.com/article/2840", "CNN", "2022-09-18"),
                News("Liz Truss energy plan ‘shows government doesn’t understand climate crisis’", "http://www.climate-news-db.com/article/7395", "The Independent", "2022-09-17"),
                News("Australia should aim for net zero by 2040, new Climate Change Authority member says | Climate crisis | The Guardian", "http://www.climate-news-db.com/article/871", "The Guardian", "2022-09-16"),
                News("People don’t really talk about climate change. Here’s how to start.", "http://www.climate-news-db.com/article/7888", "The Washington Post", "2022-09-16"),
                News("Refreezing poles to help reverse climate crisis is possible, scientists claim", "http://www.climate-news-db.com/article/7394", "The Independent", "2022-09-16"),
                News("Global warming, man-made factors worsened Pakistan floods: Study", "http://www.climate-news-db.com/article/1947", "Al Jazeera", "2022-09-16"),
                News("Climate change worsened Pakistan flood impact", "http://www.climate-news-db.com/article/6165", "The New Zealand Herald", "2022-09-16"),
                News("Climate change could spell 'disaster' for at-risk marine populations", "http://www.climate-news-db.com/article/5102", "Stuff.co.nz", "2022-09-15"),
                News("Extreme hunger soaring in world’s climate hotspots, says Oxfam | Climate crisis | The Guardian", "http://www.climate-news-db.com/article/868", "The Guardian", "2022-09-15"),
                News("Climate Change Linked to Pakistan’s Floods, Study Finds", "http://www.climate-news-db.com/article/1462", "The New York Times", "2022-09-15"),
            )
        }

        fun injectNewsSaved() : List<NewsSaved>{
            return listOf(
                NewsSaved(1,1, "2022-10-21 12:00:00"),
                NewsSaved(1,2,"2022-09-18 13:10:00"),
                NewsSaved(1,3,"2022-09-20 13:10:00"),
                NewsSaved(2,4,"2022-09-19 15:20:00"),
                NewsSaved(2,5,"2022-09-20 16:30:00"),
                NewsSaved(2,6,"2022-09-25 13:10:00")
            )
        }


        fun injectPost(context : Context): List<Post>{

            val listOfImage = listOf(R.mipmap.post1,R.mipmap.post2,R.mipmap.post3,R.mipmap.post4)
            val bitmapList : MutableList<Bitmap> = mutableListOf()
            var bitmap : Bitmap

            for(image in listOfImage){
                bitmap = BitmapFactory.decodeResource(context.resources,image)
                bitmapList.add(bitmap)
            }

            val content1 = "1.Carpooling 2.Go Green 3.Cut back on flying 4.Reduce your energy use"
            val content2 = "The world temperature being increasing, we are trying to solve this isses"
            val content3 = "Save earth, we can launch recycle program to increasing the awareness of climate action"
            val content4 = "Make our life easy"

            return listOf(
                Post("Steps to rescue Climate Change", content1, bitmapList[0], null, 0, "2022-09-28", 1),
                Post("Help World!! Save World!!", content2, bitmapList[1], null, 0, "2022-09-29", 1),
                Post("Save Earth!!", content3, bitmapList[2], null, 0, "2022-10-01", 2),
                Post("Life is going be ok!", content4, bitmapList[3], null, 0, "2022-10-01", 2)
            )
        }

        fun injectPostLiked(): List<PostLiked>{

            return listOf(
                PostLiked(1,1,"2022-09-30 10:23:00"),
                PostLiked(1,3,"2022-10-02 11:23:00"),
                PostLiked(1,4,"2022-10-01 15:10:00"),
                PostLiked(2,1,"2022-09-28 10:00:00"),
                PostLiked(2,2,"2022-09-28 11:40:00"),
            )
        }

        fun getAdmins(): List<Admin>{
            return listOf(
                Admin("Meng","meng5@gmail.com","Jalan Kuala Lumpur","0153134124","123asd"),
                Admin("Jingsen","jingsen@gmail.com","Jalan Malaysia 1","0153134124","123asd"),
                Admin("Qing Qing","qingqing1@gmail.com","Jalan Malaysia 5","0153134124","123asd",),
                Admin("Jing Den","jingen@gmail.com", "Jalan Malaysia 9", "0153134124","123asd")
            )
        }

        fun injectPostSaved(): List<PostSaved>{

            return listOf(
                PostSaved(1,1,"2022-09-30 10:23:00"),
                PostSaved(1,3,"2022-10-02 11:23:00"),
                PostSaved(2,1,"2022-09-28 10:00:00"),
                PostSaved(2,2,"2022-09-28 11:40:00"),
            )
        }

        fun injectComment() :List<Comment>{

            return listOf(
                Comment("hello 123","2022-09-28 10:23:00",null,1,1),
                Comment("Hello world 2","2022-09-29 10:23:00",null,2,1),
                Comment("Hello world 3","2022-10-01 11:30:00",null,1,3),
                Comment("Hello world 4","2022-10-02 13:00:00",null,2,2),
                Comment("Hello world 5","2022-09-30 10:23:00",null,1,2),
            )
        }


        fun injectActivityJoined(): List<ActivityJoined> {
            return listOf(
                ActivityJoined(1, 2, "2022-9-17 23:23:29"),
                ActivityJoined(1, 3, "2022-9-18 23:34:37"),
                ActivityJoined(2, 1, "2022-9-24 23:13:52"),
                ActivityJoined(2, 2, "2022-9-24 23:23:29"),
                ActivityJoined(2, 3, "2022-9-25 00:14:37")
            )
        }
    }
}