/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

/**
 * Describes the entity <b>Operation</b>
 * @author khudnitsky
 * @version 1.0
 *
 */
public class Operation extends Entity{
    private static final long serialVersionUID = 1L;
    private int userId;
    private int accountId;
    private double amount;
    private String description;
    private String date;        //TODO Calendar

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + accountId;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + userId;
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
        if (!(obj instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) obj;
        if (accountId != other.accountId) {
            return false;
        }
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        }
        else
        if (!date.equals(other.date)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        }
        else
        if (!description.equals(other.description)) {
            return false;
        }
        if (userId != other.userId) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Operation [userId=" + userId + ", accountId=" + accountId + ", amount=" + amount + ", description=" + description + ", date=" + date + "]";
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
}