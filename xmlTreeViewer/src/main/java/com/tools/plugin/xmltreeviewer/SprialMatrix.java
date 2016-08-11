/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer;

public class SprialMatrix {

    public void printMatrix(int length) {
        //       int i = size;
        Position pose = new Position(0, 4);
        System.out.println(getCenter(length));
        getValue(getCenter(length), pose, length);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
//                System.out.print("\t[" + start++ + "]");
                System.out.print("\t[" +getValue(getCenter(length),new Position(j, i), length)+ "]");
            }
            System.out.println("\n");
        }

    }

    public int getValue(Position center, Position reqired, int size) {
        if(reqired.equals(center)){
            return size*size;
        }
        int xCount = 0;
        int yCount = 0;
        int max = size * size;
        if (size % 2 == 0) {
            xCount = 1;
            yCount = -1;
        } else {
            xCount = -1;
            yCount = 1;
        }
        int currentX = center.x;
        int currentY = center.y;
        int value = max;
        boolean x = true;
        while (value > 0) {
            if (x) {
//                currentX += xCount;
//                value-=Math.abs(xCount);
                if(xCount<0){
                    for(int i=-1;i>=xCount;i--){
                        currentX --;
                        value--;        
                        if(match(currentX, currentY, reqired)){
                            return value;
                        }
                    }
                }else{
                    for(int i=1;i<=xCount;i++){
                        currentX ++;
                        value--;
                        if(match(currentX, currentY, reqired)){
                            return value;
                        }
                    }
                }
                xCount = (-1 * getNextCount(xCount)) ;
//                System.out.println("X count:"+xCount);
                
            } else {
//                currentY += yCount;
//                value-=Math.abs(yCount);
                if(yCount<0){
                    for(int i=-1;i>=yCount;i--){
                        currentY--;
                        value--;
                        if(match(currentX, currentY, reqired)){
                            return value;
                        }
                    }
                }else{
                    for(int i=1;i<=yCount;i++){
                        currentY++;
                        value--;
                        if(match(currentX, currentY, reqired)){
                            return value;
                        }
                    }
                }
                yCount = (-1 * getNextCount(yCount));
//                System.out.println("y count:"+xCount);
                
            }
            x = !x;
           
        }
        return value;
    }
    
    private boolean match(int x, int y , Position req){
        return req.equals(new Position(x, y));
//        return false;
    }
    
    private int getNextCount(int current){
        if(current>0){
            return current+1;
        }else{
            return current-1;
        }
    }
    

    private Position getCenter(int size) {
        Position pose = null;
        if (size % 2 != 0) {
            int x = size / 2;
            int y = size / 2;
            pose = new Position(x, y);
        } else {
            int x = (size - 1) / 2;
            int y = (size + 1) / 2;
            pose = new Position(x, y);
        }
        return pose;
    }

    class Position {
        int x;
        int y;

        /**
         * @param x
         * @param y
         */
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position [x=" + x + ", y=" + y + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Position other = (Position) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        private SprialMatrix getOuterType() {
            return SprialMatrix.this;
        }
        
        

    }

    public static void main(String args[]) {
        new SprialMatrix().printMatrix(5);
    }
}
