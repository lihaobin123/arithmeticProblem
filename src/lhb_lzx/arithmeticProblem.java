package lhb_lzx;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class arithmeticProblem {
    // 随机数 和 接收数值范围的变量
    private static Random random = new Random();
    public static int range;

    //主函数
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入产生题目中数值的范围：");
        range = sc.nextInt();
        System.out.println("请输入生成题目的个数：");
        int num = sc.nextInt();


        int rightcount[] = new int[num + 2];
        int wrongcount[] = new int[num + 2];
        int right1 = 0;
        int wrong1 = 0;

        //存放每道题目答案的数组
        String[] results = new String[num];

        int i;
        for (i = 0; i < num; i++) {

            String expArr[] = new String[2];//定义生成的题目

            int index = 0;
            int fenzi = 0;
            int fenmu = 0;
            int[] operators = new int[3];

            while (true) {
                //算术题中的四个数字
                String number[] = new String[4];
                for (int j = 0; j < 4; j++) {
                    number[j] = getRandomNumber(range);
                }

                //算术题中的三个运算符
                for (int j = 0; j < 3; j++) {
                    int operator = (int) (random.nextInt(4));
                    operators[j] = operator;
                }

                String str = number[0];
                String result = number[0];

                //这一遍历得出表达式 str
                for (int j = 0; j < operators.length; j++) {
                    if (operators[j] == 0) {
                        str = str + " " + '+' + " " + number[j + 1];
                    }
                    if (operators[j] == 1) {
                        str = str + " " + '-' + " " + number[j + 1];
                    }
                    if (operators[j] == 2) {
                        str = str + " " + '×' + " " + number[j + 1];
                    }
                    if (operators[j] == 3) {
                        str = str + " " + '÷' + " " + number[j + 1];
                    }
                }

                //这一遍遍历把乘除运算算完
                for (int j = 0; j < operators.length; j++) {
                    // 为乘号和除号
                    if (operators[j] > 1) {
                        //                    number[i]和number[i+1]进行运算，运算表达式另搞，结果放在number[i]中
                        for (int k = j; k >= 0; k--) {
                            if (number[k].length() != 0) {
                                index = k;
                                break;
                            }
                        }
                        int[] number1 = onlyGetNumber(number[index]);
                        int[] number2 = onlyGetNumber(number[j + 1]);

                        //转化为分子分母计算
                        int a = number1[0] * number1[2] + number1[1];
                        int b = number1[2];
                        int c = number2[0] * number2[2] + number2[1];
                        int d = number2[2];

                        if (operators[j] == 2) {
                            fenzi = a * c;
                            fenmu = b * d;
                        } else if (operators[j] == 3) {
                            fenzi = a * d;
                            fenmu = b * c;
                        }

                        result = reductionofFraction(fenzi, fenmu);
                        number[index] = result;
                        number[j + 1] = "";

                    }

                }

                //这一次遍历把加减算完，出最终result
                for (int j = 0; j < operators.length; j++) {
                    if (operators[j] <= 1) {

                        for (int k = j; k >= 0; k--) {
                            if (number[k].length() != 0) {
                                index = k;
                                break;
                            }
                        }

//                        for (int l = j; l < operators.length ; l++) {
//                            if(!"".equals(number[l])){
//                                index2 = l;
//                                break;
//                            }
//                        }

                        int[] number1 = onlyGetNumber(number[index]);
                        int[] number2 = onlyGetNumber(number[j + 1]);

                        //转化为分子分母计算
                        int a = number1[0] * number1[2] + number1[1];
                        int b = number1[2];
                        int c = number2[0] * number2[2] + number2[1];
                        int d = number2[2];

                        if (operators[j] == 0) {
                            fenzi = a * d + b * c;
                            fenmu = b * d;
                        } else if (operators[j] == 1) {
                            fenzi = a * d - b * c;
                            fenmu = b * d;
                        }

                        result = reductionofFraction(fenzi, fenmu);
                        number[index] = result;
                        number[j + 1] = "";
                    }
                }

                int[] number3 = onlyGetNumber(result);
                if(number3[0] >= 0 && number3[1] >= 0){
                    results[i] = result;
                    expArr[0] = str + " " + '=';
                    System.out.println(expArr[0]);

                    FileWriter fw = null;
                    try {

                        File f = new File("Exersies.txt");//题目写入
                        fw = new FileWriter(f, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (expArr[0] != null) {
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(i + 1 + "." + expArr[0]);
                        pw.flush();
                        try {
                            fw.flush();
                            pw.close();
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    FileWriter fn = null;
                    try {

                        File f = new File("Answer.txt");//答案写入
                        fn = new FileWriter(f, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (expArr[0] != null) {
                        PrintWriter pn = new PrintWriter(fn);
                        pn.println(i + 1 + "." + results[i]);
                        pn.flush();
                        try {
                            fn.flush();
                            pn.close();
                            fn.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                }



//                int[] temp = onlyGetNumber(result);
//                if(temp[0] >= 0 && temp[1] >= 0){
//                    expArr[0] = str + " " + '=';
//                    System.out.println(expArr[0]);
//
//                    break;
//                }

//                int temp = Integer.parseInt(result.trim());
//                Integer.parseInt(numString.trim())
//                if(temp >= 0){
//                    expArr[0] = str + " " + '=';
//                    System.out.println(expArr[0]);
//
//                    break;
//                }
            }

        }

        System.out.println("输入ok提交！");
        Scanner sc1 = new Scanner(System.in);
        String submit = sc1.nextLine();
        if (submit.equals("ok")) {
            String[] array = new String[num];
            try {
                int k = 0;
                                    //C:\Users\86155\Desktop\大三上\软件工程\arithmeticProblem\src\data
                FileReader fr = new FileReader("C:\\Users\\86155\\Desktop\\大三上\\软件工程\\arithmeticProblem\\src\\data\\Your_answers.txt");
                BufferedReader br = new BufferedReader(fr);
                String s;
                while ((s = br.readLine()) != null) {//读取小学生的答案
                    array[k] = s;
                    k++;
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                System.out.println("指定文件不存在");
            }
            for (int j = 0; j < num; j++) {
                if (array[j].equals(results[j])) {//验证答案，统计正确和错误的个数

                    rightcount[j] = j + 1;
                    right1++;
                } else {

                    wrongcount[j] = j + 1;
                    wrong1++;
                }
            }
            FileWriter fg = null;
            try {
                //反馈正确与错误题目的信息
                File f = new File("Grade.txt");
                fg = new FileWriter(f, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter pg = new PrintWriter(fg);
            pg.println(" ");
            pg.print("Correct:" + right1 + "(");
            for (int j = 0; j <= num; j++) {
                if (rightcount[j] != 0) {
                    pg.print(rightcount[j] + ",");
                }
            }
            pg.println(")");
            pg.print("Wrong:" + wrong1 + "(");
            for (int j = 0; j <= num; j++) {
                if (wrongcount[j] != 0) {
                    pg.print(wrongcount[j] + ",");
                }
            }
            pg.print(")");
            pg.flush();
            try {
                fg.flush();
                pg.close();
                fg.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //生成随机的数字（自然数或分数）
    public static String getRandomNumber(int range){
        if(random.nextInt(1) == 0){
//            该数字为范围内的分数
            int a = (int) (random.nextInt(range));//分子
            int b = (int) (random.nextInt(range) + 1);//分母

            return reductionofFraction(a,b);
        }else {
//            该数字为范围内的自然数
            int c = (int) (random.nextInt(range)+1);
            return c + "";
        }
    }

    // 将字符串中的数字提出来
    public static int[] onlyGetNumber(String str){
//        str = str.trim();
//        String str2 = new String();
//        if(str != null && !"".equals(str)){
//            for(int i = 0;i < str.length(); i++){
//                if(str.charAt(i) >= 48 && str.charAt(i) <= 57){
//                    str2 += str.charAt(i);
//                }
//            }
//        }
//        int temp = Integer.parseInt(str2.trim());
//        int[] temp2 = new int[3];
//        if(temp >= 0 && temp < 10){
//            temp2[0] = temp;
//            temp2[1] = 0;
//            temp2[2] = 1;
//        }else if(temp >= 10 && temp < 100){
//            temp2[0] = 0;
//            temp2[1] = temp / 10;
//            temp2[2] = temp % 10;
//        }else if(temp >= 100){
//            temp2[0] = temp / 100;
//            temp2[1] = (temp /10)%10;
//            temp2[2] = temp % 10;
//        }
//
//        return temp2;
        int index1 = str.indexOf("'");
        int index2 = str.indexOf("/");
        String strA = new String();
        String strB = new String();
        String strC = new String();

        int[] temp = new int[3];

        if(index1 != -1 && index2 != -1){//帶分數
            strA = str.substring(0, index1);
            strB = str.substring(index1+1,index2);
            strC = str.substring(index2+1);

            temp[0] = Integer.parseInt(strA.trim());
            temp[1] = Integer.parseInt(strB.trim());
            temp[2] = Integer.parseInt(strC.trim());
        } else if(index1 == -1 && index2 == -1){//整數
            temp[0] = Integer.parseInt(str.trim());
            temp[1] = 0;
            temp[2] = 1;
        } else if (index1 == -1 && index2 != -1){//分數

            strB = str.substring(0,index2);
            strC = str.substring(index2+1);

            temp[0] = 0;
            temp[1] = Integer.parseInt(strB.trim());
            temp[2] = Integer.parseInt(strC.trim());
        }

        return temp;
    }

    //将字符串数字改为整型数据
//    public static int changeStringNumber(String str){
//        int temp = Integer.parseInt(str);
//
//        if(temp % 10 == 0 && temp /10 %10 == 0){
//            return temp / 100;
//        }else if(temp % 10 == 0){
//            return temp / 10;
//        }else{
//            return temp;
//        }
//    }


    // 约分，分数约分函数，用于计算结果
    public static String reductionofFraction(int a, int b) {
        // 求出公约数赋值给y
        int y = 1;
        for (int i = a; i >= 1; i--) {
            if (a % i == 0 && b % i == 0) {
                y = i;
                break;
            }
        }

        int z = a / y;// 分子
        int m = b / y;// 分母
        if (z == 0) {
            return "0";
        }
        if (m == 1) return z + "";
        else return biaodashi(z, m);

    }

    //化简，判断假分数，并化假分数为带分数
    public static String biaodashi(int a, int b) {
        if (a >= b && b != 0) {
            int c;
            c = a / b;
            int d;
            d = a % b;

            if (d == 0) {
                return c + "";
            }
            return c + "'" + d + "/" + b;

        }
        return a + "/" + b;
    }
}
