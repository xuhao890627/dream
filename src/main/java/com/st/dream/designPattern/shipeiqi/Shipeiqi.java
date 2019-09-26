package com.st.dream.designPattern.shipeiqi;

import java.io.*;

public class Shipeiqi {

    //适配器

    //
    public static void main(String[] args) throws FileNotFoundException {
        Reader r = new InputStreamReader(new FileInputStream(new File("")));




    }

    //给出的b接口  要执行a的东西
    public void testShipeiqi() {
        b b = new xx();
        b.bb();
    }


    private interface a{
        void aa();
    }

    private interface b{
        void bb();
    }

    private class al implements a{

        @Override
        public void aa() {

        }
    }

    private class xx extends al implements b{

        @Override
        public void bb() {
            aa();
        }
    }
}
