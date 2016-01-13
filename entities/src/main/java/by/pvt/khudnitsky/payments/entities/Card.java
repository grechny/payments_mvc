/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

/**
 * Describes the entity <b>Card</b>
 * @author khudnitsky
 * @version 1.0
 *
 */
public class Card extends Entity{
    private static final long serialVersionUID = 1L;
    private int accountId;
    private String validity;    // TODO Calendar

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + accountId;
        result = prime * result + ((validity == null) ? 0 : validity.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        Card other = (Card) obj;
        if (accountId != other.accountId) {
            return false;
        }
        if (validity == null) {
            if (other.validity != null) {
                return false;
            }
        }
        else
        if (!validity.equals(other.validity)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Card [accountId=" + accountId + ", validity=" + validity + "]";
    }

    /**
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the validity
     */
    public String getValidity() {
        return validity;
    }

    /**
     * @param validity the validity to set
     */
    public void setValidity(String validity) {
        this.validity = validity;
    }

}
