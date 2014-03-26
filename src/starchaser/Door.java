/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package starchaser;

/**
 *
 * @author regen
 */

public class Door extends Tile{    
    public Door(int x, int y, int tileSize){
        super(x, y, (int)(tileSize*0.75), tileSize, AssetHandler.getDoor());
    }  
}
