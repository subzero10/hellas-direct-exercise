package gr.hellasdirect.houseinsurance;

public class HouseDetails {
    private String postCode;
    private boolean thatchedRoof;
    private int numBedrooms;

    public HouseDetails() {
        this.postCode = null;
        this.thatchedRoof = false;
        this.numBedrooms = 0;
    }

    public boolean hasThatchedRoof() {
        return thatchedRoof;
    }

    public HouseDetails setThatchedRoof(boolean thatchedRoof) {
        this.thatchedRoof = thatchedRoof;
        return this;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public HouseDetails setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public HouseDetails setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }
}
