package com.jetusesoft.warehousemanagement.entity;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Connection implements Serializable {
    private String host;
    private int port;
    private boolean isConnectSuccessful;

    public Connection(String host, int port, boolean isConnectSuccessful) {
        this.host = host;
        this.port = port;
        this.isConnectSuccessful = isConnectSuccessful;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConnectSuccessful() {
        return isConnectSuccessful;
    }

    public void setConnectSuccessful(boolean connectSuccessful) {
        isConnectSuccessful = connectSuccessful;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Connection other = (Connection)obj;
        return host.equals(other.host) && port == other.port && isConnectSuccessful == other.isConnectSuccessful;
    }

    @Override
    public int hashCode() {
        return (host == null? host.hashCode() : 0) + new Integer(port).hashCode() + new Boolean(isConnectSuccessful).hashCode();

    }
}
