package com.fyp.melody;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Menus {

    public String MenuName;
    public String MenuPrice;
    private String CompanyImage;

    public Menus(){
    }

    public Menus(String menuName, String menuPrice, String compImage) {
        MenuName = menuName;
        MenuPrice = menuPrice;
        CompanyImage = compImage;
    }

    public Menus(Json2Menu jPromo){
        MenuName = jPromo.menuName;
        MenuPrice = jPromo.menuPrice;
        CompanyImage = jPromo.companyImage;
    }


    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName (String promoDesc) {
        MenuName = promoDesc;
    }

    public String getMenuPrice() {
        return MenuPrice;
    }

    public void setMenuPrice (String promoPrice) {
        MenuPrice = promoPrice;
    }

    public String getCompanyImage() {
        return CompanyImage;
    }

    public void setCompanyImage(String compImage) {
        CompanyImage = compImage;
    }

}
