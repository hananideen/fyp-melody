package com.fyp.melody;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Menus {

    public String CompanyName;
    public String Description;
    public String PromoPrice;
    public String DueDate;
    private String PromotionImage;
    private int CompanyImage;
    private int No_of_Likes;
    private int No_of_Dislikes;
    private boolean LikeButtonClicked, DislikeButtonClicked;

    public Menus(){
        LikeButtonClicked = false;
        DislikeButtonClicked = false;
    }

    public Menus(String companyName, String promoDesc, String promoPrice, String dueDate, String promoImage, int compImage, int no_of_Likes, int no_of_Dislikes) {
        CompanyName = companyName;
        Description = promoDesc;
        PromoPrice = promoPrice;
        DueDate = dueDate;
        PromotionImage = promoImage;
        CompanyImage = compImage;
        No_of_Likes = no_of_Likes;
        No_of_Dislikes = no_of_Dislikes;
        DislikeButtonClicked = false;
        LikeButtonClicked = false;
    }

    public Menus(Json2Menu jPromo){
        CompanyName = jPromo.companyName;
        Description = jPromo.productDescription;
        PromoPrice = jPromo.productPrice;
        DueDate = jPromo.DueDate;
        No_of_Likes = jPromo.No_of_Likes;
        No_of_Dislikes = jPromo.No_of_Dislikes;
        PromotionImage = jPromo.productImage;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String promoDesc) {
        Description = promoDesc;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPromoPrice() {
        return PromoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        PromoPrice = promoPrice;
    }

    public String getPromotionImage() {
        return PromotionImage;
    }

    public void setPromotionImage(String promoImage) {
        PromotionImage = promoImage;
    }

    public int getCompanyImage() {
        return CompanyImage;
    }

    public void setCompanyImage(int compImage) {
        CompanyImage = compImage;
    }

}
