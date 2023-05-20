package com.google.zxing.client.result;

import java.util.Map;

public final class ExpandedProductParsedResult extends ParsedResult {
    public static final String KILOGRAM = "KG";
    public static final String POUND = "LB";
    private final String bestBeforeDate;
    private final String expirationDate;
    private final String lotNumber;
    private final String packagingDate;
    private final String price;
    private final String priceCurrency;
    private final String priceIncrement;
    private final String productID;
    private final String productionDate;
    private final String sscc;
    private final Map<String, String> uncommonAIs;
    private final String weight;
    private final String weightIncrement;
    private final String weightType;

    public ExpandedProductParsedResult(String productID, String sscc, String lotNumber, String productionDate, String packagingDate, String bestBeforeDate, String expirationDate, String weight, String weightType, String weightIncrement, String price, String priceIncrement, String priceCurrency, Map<String, String> uncommonAIs) {
        super(ParsedResultType.PRODUCT);
        this.productID = productID;
        this.sscc = sscc;
        this.lotNumber = lotNumber;
        this.productionDate = productionDate;
        this.packagingDate = packagingDate;
        this.bestBeforeDate = bestBeforeDate;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.weightType = weightType;
        this.weightIncrement = weightIncrement;
        this.price = price;
        this.priceIncrement = priceIncrement;
        this.priceCurrency = priceCurrency;
        this.uncommonAIs = uncommonAIs;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ExpandedProductParsedResult)) {
            return false;
        }
        ExpandedProductParsedResult other = (ExpandedProductParsedResult) o;
        if (equalsOrNull(this.productID, other.productID) && equalsOrNull(this.sscc, other.sscc) && equalsOrNull(this.lotNumber, other.lotNumber) && equalsOrNull(this.productionDate, other.productionDate) && equalsOrNull(this.bestBeforeDate, other.bestBeforeDate) && equalsOrNull(this.expirationDate, other.expirationDate) && equalsOrNull(this.weight, other.weight) && equalsOrNull(this.weightType, other.weightType) && equalsOrNull(this.weightIncrement, other.weightIncrement) && equalsOrNull(this.price, other.price) && equalsOrNull(this.priceIncrement, other.priceIncrement) && equalsOrNull(this.priceCurrency, other.priceCurrency) && equalsOrNull(this.uncommonAIs, other.uncommonAIs)) {
            return true;
        }
        return false;
    }

    private static boolean equalsOrNull(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

    public int hashCode() {
        return ((((((((((((0 ^ hashNotNull(this.productID)) ^ hashNotNull(this.sscc)) ^ hashNotNull(this.lotNumber)) ^ hashNotNull(this.productionDate)) ^ hashNotNull(this.bestBeforeDate)) ^ hashNotNull(this.expirationDate)) ^ hashNotNull(this.weight)) ^ hashNotNull(this.weightType)) ^ hashNotNull(this.weightIncrement)) ^ hashNotNull(this.price)) ^ hashNotNull(this.priceIncrement)) ^ hashNotNull(this.priceCurrency)) ^ hashNotNull(this.uncommonAIs);
    }

    private static int hashNotNull(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    public String getProductID() {
        return this.productID;
    }

    public String getSscc() {
        return this.sscc;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public String getProductionDate() {
        return this.productionDate;
    }

    public String getPackagingDate() {
        return this.packagingDate;
    }

    public String getBestBeforeDate() {
        return this.bestBeforeDate;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getWeight() {
        return this.weight;
    }

    public String getWeightType() {
        return this.weightType;
    }

    public String getWeightIncrement() {
        return this.weightIncrement;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPriceIncrement() {
        return this.priceIncrement;
    }

    public String getPriceCurrency() {
        return this.priceCurrency;
    }

    public Map<String, String> getUncommonAIs() {
        return this.uncommonAIs;
    }

    public String getDisplayResult() {
        return String.valueOf(this.productID);
    }
}
