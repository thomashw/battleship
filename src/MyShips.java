import java.util.Random;

/**
 * Author: Thomas Hewton-Waters
 * Created on: February 03, 2012
 */

/* Holds pre-determined locations I would like to place my ships */
public class MyShips {
    private Ship[] myFFs;
    private Ship[] mySSKs;
    private Ship[] myDDHs;
    private Ship[] myBBs;
    private Ship[] myCVLs;

    private String[] shipLayouts;

    public MyShips()
    {
        myFFs = new Ship[Const.kNumShipLayouts];
        mySSKs = new Ship[Const.kNumShipLayouts];
        myDDHs = new Ship[Const.kNumShipLayouts];
        myBBs = new Ship[Const.kNumShipLayouts];
        myCVLs = new Ship[Const.kNumShipLayouts];

        /* FF layouts. Must not overlap other layouts at same index. */
        myFFs[0] = new Ship("9,1,1;9,1,2;9,1,3", Const.kShipNameFF);
        myFFs[1] = new Ship("8,1,2;8,1,3;8,1,4", Const.kShipNameFF);
        myFFs[2] = new Ship("3,8,0;3,7,0;3,6,0", Const.kShipNameFF);
        myFFs[3] = new Ship("2,1,8;2,1,7;2,1,6", Const.kShipNameFF);
        myFFs[4] = new Ship("8,8,1;8,7,1;8,6,1", Const.kShipNameFF);
        myFFs[5] = new Ship("9,8,1;9,7,1;9,6,1", Const.kShipNameFF);
        myFFs[6] = new Ship("5,1,1;5,1,2;5,1,3", Const.kShipNameFF);
        myFFs[7] = new Ship("1,9,8;1,8,8;1,7,8", Const.kShipNameFF);
        myFFs[8] = new Ship("9,1,8;9,2,8;9,3,8", Const.kShipNameFF);
        myFFs[9] = new Ship("8,8,1;8,8,2;8,8,3", Const.kShipNameFF);

        /* SSK layouts. Must not overlap other layouts at same index. */
        mySSKs[0] = new Ship("0,1,9;0,1,8;0,1,7;0,1,6;0,1,5", Const.kShipNameSSK);
        mySSKs[1] = new Ship("9,2,8;9,2,7;9,2,6;9,2,5;9,2,4", Const.kShipNameSSK);
        mySSKs[2] = new Ship("5,9,1;5,9,2;5,9,3;5,9,4;5,9,5", Const.kShipNameSSK);
        mySSKs[3] = new Ship("6,8,1;6,7,1;6,6,1;6,5,1;6,4,1", Const.kShipNameSSK);
        mySSKs[4] = new Ship("6,2,1;6,3,1;6,4,1;6,5,1;6,6,1", Const.kShipNameSSK);
        mySSKs[5] = new Ship("5,8,8;5,7,8;5,6,8;5,5,8;5,4,8", Const.kShipNameSSK);
        mySSKs[6] = new Ship("3,1,1;3,1,2;3,1,3;3,1,4;3,1,5", Const.kShipNameSSK);
        mySSKs[7] = new Ship("9,1,9;9,2,9;9,3,9;9,4,9;9,5,9", Const.kShipNameSSK);
        mySSKs[8] = new Ship("7,1,8;7,2,8;7,3,8;7,4,8;7,5,8", Const.kShipNameSSK);
        mySSKs[9] = new Ship("0,8,5;0,8,6;0,8,7;0,8,8;0,8,9", Const.kShipNameSSK);

        /* DDH layouts. Must not overlap other layouts at same index. */
        myDDHs[0] = new Ship("1,9,1;1,8,1;1,7,1;1,8,0;1,8,2", Const.kShipNameDDH);
        myDDHs[1] = new Ship("5,8,8;5,7,8;5,6,8;6,7,8;4,7,8", Const.kShipNameDDH);
        myDDHs[2] = new Ship("6,2,7;6,2,8;6,2,9;5,2,8;7,2,8", Const.kShipNameDDH);
        myDDHs[3] = new Ship("0,9,3;0,8,3;0,7,3;0,8,2;0,8,4", Const.kShipNameDDH);
        myDDHs[4] = new Ship("9,7,9;9,7,8;9,7,7;9,8,8;9,6,8", Const.kShipNameDDH);
        myDDHs[5] = new Ship("7,1,0;7,1,1;7,1,2;7,2,1;7,0,1", Const.kShipNameDDH);
        myDDHs[6] = new Ship("4,8,1;4,8,2;4,8,3;4,7,2;4,9,2", Const.kShipNameDDH);
        myDDHs[7] = new Ship("3,6,7;3,7,7;3,8,7;2,7,7;4,7,7", Const.kShipNameDDH);
        myDDHs[8] = new Ship("1,8,4;1,8,5;1,8,6;0,8,5;2,8,5", Const.kShipNameDDH);
        myDDHs[9] = new Ship("8,2,8;8,2,7;8,2,6;9,2,7;7,2,7", Const.kShipNameDDH);

        /* BB layouts. Must not overlap other layouts at same index. */
        myBBs[0] = new Ship("1,8,8;1,7,8;1,6,8;1,6,7;1,6,6;1,7,6;1,8,6;1,8,7", Const.kShipNameBB);
        myBBs[1] = new Ship("1,4,0;1,3,0;1,2,0;1,2,1;1,2,2;1,3,2;1,4,2;1,4,1", Const.kShipNameBB);
        myBBs[2] = new Ship("0,8,5;0,8,6;0,8,7;0,7,7;0,6,7;0,6,6;0,6,5;0,7,5", Const.kShipNameBB);
        myBBs[3] = new Ship("1,3,1;1,3,2;1,3,3;2,3,3;3,3,3;3,3,2;3,3,1;2,3,1", Const.kShipNameBB);
        myBBs[4] = new Ship("2,7,9;2,8,9;2,9,9;2,9,8;2,9,7;2,8,7;2,7,7;2,7,8", Const.kShipNameBB);
        myBBs[5] = new Ship("8,3,5;8,3,6;8,3,7;8,4,7;8,5,7;8,5,6;8,5,5;8,4,5", Const.kShipNameBB);
        myBBs[6] = new Ship("9,4,1;9,4,2;9,4,3;9,3,3;9,2,3;9,2,2;9,2,1;9,3,1", Const.kShipNameBB);
        myBBs[7] = new Ship("7,6,8;7,5,8;7,4,8;6,4,8;5,4,8;5,5,8;5,6,8;6,6,8", Const.kShipNameBB);
        myBBs[8] = new Ship("4,4,9;4,5,9;4,6,9;4,6,8;4,6,7;4,5,7;4,4,7;4,4,8", Const.kShipNameBB);
        myBBs[9] = new Ship("2,6,4;2,6,5;2,6,6;2,7,6;2,8,6;2,8,5;2,8,4;2,7,4", Const.kShipNameBB);

        /* CVL layouts. Must not overlap other layouts at same index. */
        myCVLs[0] = new Ship("7,8,5;7,7,5;7,6,5;7,5,5;7,4,5;7,6,4;7,6,3;7,6,6;7,6,7;6,6,5;5,6,5;8,6,5;9,6,5", Const.kShipNameCVL);
        myCVLs[1] = new Ship("2,7,2;2,7,3;2,7,4;2,7,5;2,7,6;2,8,4;2,9,4;2,6,4;2,5,4;1,7,4;0,7,4;3,7,4;4,7,4", Const.kShipNameCVL);
        myCVLs[2] = new Ship("6,4,3;6,5,3;6,6,3;6,7,3;6,8,3;6,6,4;6,6,5;6,6,2;6,6,1;5,6,3;4,6,3;7,6,3;8,6,3", Const.kShipNameCVL);
        myCVLs[3] = new Ship("1,7,7;2,7,7;3,7,7;4,7,7;5,7,7;3,8,7;3,9,7;3,6,7;3,5,7;3,7,8;3,7,9;3,7,6;3,7,5", Const.kShipNameCVL);
        myCVLs[4] = new Ship("5,2,4;5,2,5;5,2,6;5,2,7;5,2,8;6,2,6;7,2,6;4,2,6;3,2,6;5,1,6;5,0,6;5,3,6;5,4,6", Const.kShipNameCVL);
        myCVLs[5] = new Ship("9,5,2;8,5,2;7,5,2;6,5,2;5,5,2;7,6,2;7,7,2;7,4,2;7,3,2;7,5,1;7,5,0;7,5,3;7,5,4", Const.kShipNameCVL);
        myCVLs[6] = new Ship("2,4,8;2,4,7;2,4,6;2,4,5;2,4,4;1,4,6;0,4,6;3,4,6;4,4,6;2,3,6;2,2,6;2,5,6;2,6,6", Const.kShipNameCVL);
        myCVLs[7] = new Ship("5,8,4;5,7,4;5,6,4;5,5,4;5,4,4;4,6,4;3,6,4;6,6,4;7,6,4;5,6,3;5,6,2;5,6,5;5,6,6", Const.kShipNameCVL);
        myCVLs[8] = new Ship("8,6,3;7,6,3;6,6,3;5,6,3;4,6,3;6,7,3;6,8,3;6,5,3;6,4,3;6,6,2;6,6,1;6,6,4;6,6,5", Const.kShipNameCVL);
        myCVLs[9] = new Ship("6,2,3;5,2,3;4,2,3;3,2,3;2,2,3;4,1,3;4,0,3;4,3,3;4,4,3;4,2,2;4,2,1;4,2,4;4,2,5", Const.kShipNameCVL);
    }
    
    public String generateLayout()
    {
        Random random = new Random();
        int rand = random.nextInt( Const.kNumShipLayouts );

        if( rand >= Const.kNumShipLayouts )
            return getLayout(0);

        return "FF:" + myFFs[rand].coordinates +
                "|SSK:" + mySSKs[rand].coordinates +
                "|DDH:" + myDDHs[rand].coordinates +
                "|BB:" + myBBs[rand].coordinates +
                "|CVL:" + myCVLs[rand].coordinates;
    }

    public String getLayout( int index )
    {
        if( index < 0 || index >= Const.kNumShipLayouts )
            return "FF:9,0,0;9,0,1;9,0,2|SSK:9,0,3;9,0,4;9,0,5;9,0,6;9,0,7|DDH:0,1,0;1,0,0;1,1,0;1,2,0;2,1,0|BB:1,1,1;1,1,2;1,1,3;1,2,1;1,2,3;1,3,1;1,3,2;1,3,3|CVL:0,2,2;1,2,2;2,2,2;3,2,2;4,2,2;2,0,2;2,1,2;2,3,2;2,4,2;2,2,0;2,2,1;2,2,3;2,2,4";
        else
            return "FF:" + myFFs[index].coordinates +
                    "|SSK:" + mySSKs[index].coordinates +
                    "|DDH:" + myDDHs[index].coordinates +
                    "|BB:" + myBBs[index].coordinates +
                    "|CVL:" + myCVLs[index].coordinates;
    }
}
