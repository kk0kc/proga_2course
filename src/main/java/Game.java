import java.io.IOException;

public class Game implements Runnable {
    Client client1;
    Client client2;
    Game game;
    static int[] array;
    static int count;

    public Game(Client client1, Client client2) throws IOException {
        this.client1 = client1;
        this.client2 = client2;
        array = new int[9];
        count = 0;
    }

    @Override
    public void run() {

        //initial state
        client1.write("initial state\n" + Game.arrayToString());
        client2.write("initial state\n" + Game.arrayToString());

        boolean exit = false;
        boolean chance = true;
        String c1Mark = "x";
        String c2Mark = "o";

        while (!exit) {
            if (chance) {
                exit = play(client1, client2, c1Mark);
                chance = false;
            }
            else {
                exit = play(client2, client1, c2Mark);
                chance = true;
            }
        }
        client1.close();
        client2.close();
    }

    private boolean play(Client c1, Client c2, String mark){
        String msg;
        boolean marked = false;
        int m = mark.equals("x") ? 1 : -1;
        while (!marked) {
            c1.write("Please enter from 1-9: ");
            msg = c1.read();
            if (markZeroesCrosses(Integer.parseInt(msg), m)){
            Game.cardinal(msg, mark);
            marked = true;}
            else
            {c1.write("Position already occupied\nSelect another");}

        }

        c1.write(Game.arrayToString());
        c2.write(Game.arrayToString());
        if (threes(m == 1 ? 3 : -3)) {
            c1.write("\nCongrats!!!\nYou won ");
            c2.write("\nYou Lost ");
            return true;
        } else {
            c1.write("Wait for opponents move");
            c2.write("Your move!");
        }
        return false;
    }
    public static void cardinal(String msg, String mark){
        int position = Integer.parseInt(msg);
        int m = mark.equals("x") ? 1 : -1;
        markZeroesCrosses(position, m);
        continueGameOrNot(m);

    }

    private static boolean markZeroesCrosses(int position, int mark)  {
        boolean marked = true;
        if (array[position - 1] == 0) {
            array[position - 1] = mark;
            count++;
        } else {marked = false;}
        return marked;
    }

    private static boolean continueGameOrNot(int mark){
        if (count == 9){
        } else {
            threes(mark == 1 ? 3 : -3);}
        return true;
    }

    private static boolean threes(int sum)  {
        boolean won = false;
        if (array[0] + array[1] + array[2] == sum) {won = true;}
        else if (array[0] + array[3] + array[6] == sum){won = true;}
        else if (array[2] + array[5] + array[8] == sum){won = true;}
        else if (array[6] + array[7] + array[8] == sum){won = true;}
        else if (array[0] + array[4] + array[8] == sum){won = true;}
        else if (array[1] + array[4] + array[7] == sum){won = true;}
        else if (array[2] + array[4] + array[6] == sum){won = true;}
        else if (array[3] + array[4] + array[5] == sum){won = true;}
        return won;
    }

    public static String arrayToString() {
        String msg = "";
        for (int i = 1; i < 10; i++) {
            String mark = array[i - 1] == 0 ? " " : array[i - 1] == -1 ? "O" : "X";
            msg += String.format("%2s",mark);
            if (i % 3 == 0) {
                msg += "\n---------\n";
            }
            else {
                msg += "|";
            }
        }
        msg += "\n";
        return msg;
    }

}
