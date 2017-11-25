package com.example.guest.today_dobi;

/**
 * Created by guest on 2017. 11. 25..
 */

class Listing_Logic {
    public static String main(String test_message){


        String return_message = "";

        if(test_message.contains("택배")){
            return_message = "형직아 택배 받아라!";
        }else if(test_message.contains("배달")){
            return_message = "형직아 또 뭐먹냐";
        }



        return return_message;
    }
}
