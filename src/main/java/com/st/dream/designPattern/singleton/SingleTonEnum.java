package com.st.dream.designPattern.singleton;

public class SingleTonEnum {

    public static SingleTonEnum getInstance(){
        return Elvis.INSTANCE.getInstance();
    }

    private enum Elvis{
        INSTANCE;

        private SingleTonEnum instance;

        Elvis() {
            instance = new SingleTonEnum();
        }

        private SingleTonEnum getInstance() {
            return instance;
        }
    }
}

