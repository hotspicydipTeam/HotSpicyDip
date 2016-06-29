package com.scnu.hotspicydip.view;

import java.util.ArrayList;

public class StringPellet {

      public ArrayList<GetPellet> pellets;

             //鱼丸最大数量
    int maxCount;

     public StringPellet() {
          pellets = new ArrayList<GetPellet>();
         int maxCount = 8;
     }

     public void addPellet(GetPellet p) {          // 添加鱼丸

         if(pellets.size() >= 2 && pellets.get(pellets.size()-1).pelletType == p.pelletType
                 && pellets.get(pellets.size()-2).pelletType == p.pelletType ) {
             pellets.remove(pellets.size() - 1);
             pellets.remove(pellets.size() - 1);           //3个相同，消除鱼丸
         }
//         else if(pellets.size() == maxCount)
//         {
//             //游戏结束
//         }
         else
             pellets.add(p);

     }

}
