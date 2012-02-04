/**
 * Author: Thomas Hewton-Waters
 * Created on: 01 31, 2012
 */

public class EnemyShips {
    public enum ShipStatus {
        ShipStatusAlive,
        ShipStatusSunk
    }

    public ShipStatus FF;
    public ShipStatus SSK;
    public ShipStatus DDH;
    public ShipStatus BB;
    public ShipStatus CVL;

    public EnemyShips()
    {
        FF = ShipStatus.ShipStatusAlive;
        SSK = ShipStatus.ShipStatusAlive;
        DDH = ShipStatus.ShipStatusAlive;
        BB = ShipStatus.ShipStatusAlive;
        CVL = ShipStatus.ShipStatusAlive;
    }
}
