package com.oneshoppoint.yates.wrapper;


import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by robinson on 5/20/16.
 */
public class Settings {
    private String logo;
    @NotNull
    private String branding;
    @Size(min=50,message = "Legal information must be greater than 50 characters")
    private String legalPublic;
    @Size(min=50,message = "Legal information must be greater than 50 characters")
    private String legalRetailer;
    @Size(min=50,message = "Legal information must be greater than 50 characters")
    private String legalMedic;
    @Size(min=50,message = "Legal information must be greater than 50 characters")
    private String legalCarrier;
    @Size(min=10,max = 100,message = "about us should be brief , that is a maximum of 50 characters")
    private String aboutUs;
    private String company;
    @NotNull
    private String phone;
    private String street;
    private String residence;
    private String location;
    private String country;
    private String facebook;
    private String googlePlus;
    private String twitter;
    @Email
    private String email;

    public void setLogo (String logo) {
        this.logo = logo;
    }

    public String getLogo () {
        return logo;
    }

    public void setBranding (String branding) {
        this.branding = branding;
    }

    public String getBranding () {
        return branding;
    }

    public void setLegalPublic (String legalPublic) {
        this.legalPublic = legalPublic;
    }

    public String getLegalPublic () {
        return legalPublic;
    }

    public void setLegalRetailer (String legalRetailer) {
        this.legalRetailer = legalRetailer;
    }

    public String getLegalRetailer () {
        return legalRetailer;
    }

    public void setLegalMedic (String legalMedic) {
        this.legalMedic = legalMedic;
    }

    public String getLegalMedic () {
        return legalMedic;
    }

    public void setLegalCarrier (String legalCarrier) {
        this.legalCarrier = legalCarrier;
    }

    public String getLegalCarrier () {
        return legalCarrier;
    }

    public void setAboutUs (String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getAboutUs () {
        return aboutUs;
    }

    public void setCompany (String company) {
        this.company = company;
    }

    public String getCompany () {
        return company;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getPhone () {
        return phone;
    }

    public void setStreet (String street) {
        this.street = street;
    }

    public String getStreet () {
        return street;
    }

    public void setResidence (String residence) {
        this.residence = residence;
    }

    public String getResidence () {
        return residence;
    }

    public void setLocation (String location) {
        this.location = location;
    }

    public String getLocation () {
        return location;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public String getCountry () {
        return country;
    }

    public void setFacebook (String facebook) {
        this.facebook = facebook;
    }

    public String getFacebook () {
        return facebook;
    }

    public void setTwitter (String twitter) {
        this.twitter = twitter;
    }

    public String getTwitter () {
        return twitter;
    }

    public void setGooglePlus (String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getGooglePlus () {
        return googlePlus;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail () {
        return email;
    }

    public String toString () {
        String str = "";
        if(logo != null && logo.length() > 0) {
            str = "logo ="+ logo +"\n";
        }
        str+= "branding = "+branding + "\n";
        str+= "company = "+company + "\n";
        str+= "phone = "+phone + "\n";
        str+= "street = "+street + "\n";
        str+= "residence = "+residence + "\n";
        str+= "location = "+location + "\n";
        str+= "country = "+country + "\n";
        str+= "facebook = "+facebook+ "\n";
        str+= "twitter = "+twitter+ "\n";
        str+= "google_plus = "+googlePlus+ "\n";
        str+= "email = "+email+ "\n";
        return str;
    }
}
