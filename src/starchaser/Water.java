/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package starchaser;

import starchaser.Tile.Tile;

public class Water extends Tile {
    
    public Water(int x, int y, int tileSize){
        super(x, y, tileSize, tileSize, AssetHandler.getWater());
    }
}
