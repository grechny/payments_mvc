/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

/**
 * Describes the entity <b>Account</b>
 * @author khudnitsky
 * @version 1.0
 *
 */
public class Account extends Entity{
    private static final long serialVersionUID = 1L;
    private String currency;
    private double amount;
    private int status;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + status;
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
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account other = (Account) obj;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (currency == null) {
            if (other.currency != null) {
                return false;
            }
        }
        else
        if (!currency.equals(other.currency)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Account [currency=" + currency + ", amount=" + amount + ", status=" + status + "]";
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
