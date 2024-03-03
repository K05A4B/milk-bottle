<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="UTF-8" />
    <meta
      name="description"
      content="牛奶瓶 Mod, 当然不止牛奶瓶, 我还添加了去除某个特定效果的药水以及强大的清害药水"
    />
    <meta
      name="keywords"
      content="milk bottle,minecraft mods,我的世界,模组,Java,mc,mod,fabric mod,fabric"
    />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="./resources/style/global.css" />
    <link rel="stylesheet" href="./resources/style/table.css">
    <link rel="stylesheet" href="./resources/style/recipe.css">
    <link rel="stylesheet" href="./resources/style/hero-section.css" />
    <link rel="stylesheet" href="./resources/style/documentations.css">
    <title>Milk bottle - 牛奶瓶</title>
  </head>
  <body>
    <main id="app">
      <div id="hero-section">
        <div class="texts">
          <img src="./assets/imgs/mod_texture/milk_bottle.png" alt="milk bottle" />
          <h1>Milk Bottle</h1>
          <p>Milk Bottle(牛奶瓶) 原版拓展模组</p>
          <p>
            添加了<kbd>牛奶瓶</kbd>、<kbd>清害药水</kbd>、<kbd>抗挖掘疲劳药水</kbd> 等物品
          </p>
          <p>下载提取码: <kbd>1m0k</kbd></p>
        </div>
        <div class="buttons">
          <a target="_blank" href="https://wwm.lanzoum.com/b029y1ibg">
            <button>下载</button>
          </a>
          <a href="#milk-bottle">了解更多</a>
        </div>
      </div>
      <div id="docs">
        <div class="section">
          <h1 id="milk-bottle">牛奶瓶</h1>
          <p>与奶桶有着相同作用的可堆叠的物品</p>
          <p>您可以通过一个奶桶和3个<span style="color: tomato;">空玻璃瓶</span>合成 (无序合成, 不消耗铁桶)</p>
          <p>牛奶瓶最大可堆叠12个 (4桶奶)</p>
          <div class="recipe">
            $glass_bottle: ./assets/imgs/glass_bottle.png;
            (1,1): $glass_bottle;
            (2,1): $glass_bottle;
            (2,2): $glass_bottle;
            (1,2): ./assets/imgs/milk_bucket.png;
            result: ./assets/imgs/mod_texture/milk_bottle.png;
            count: 3;
          </div>
        </div>
        <div class="section">
          <h1>负面效果解药</h1>
          <p>这些药水能解除某个特定负面的效果, 例如 挖掘疲劳 等</p>
          <p>合成配方一般是3个<span style="color: tomato;">牛奶瓶</span>加某个材料 (无序合成)</p>
          <p>例如抗挖掘疲劳药水的配方</p>
          <div class="recipe">
            $m: ./assets/imgs/mod_texture/milk_bottle.png;
            (1,1): $m;
            (2,1): $m;
            (2,2): $m;
            (1,2): ./assets/imgs/prismarine_shard.png;
            result: ./assets/imgs/mod_texture/mining_fatigue_antidote.png;
            count: 3;
          </div>
          <p>下面是关于材料和效果的对应表, 可根据需求替换材料</p>
          <table id="antidote-materials-list" border>
            <tr>
              <th>材料</th>
              <td>材料图片</td>
              <th>效果</th>
            </tr>
          </table>
        </div>
        <div class="section">
          <h1>清害药水</h1>
          <p>此药水会清除所有的负面效果, 并且给予玩家 25秒的 伤害吸收I 和 3秒的 生命恢复III </p>
          <p>此药水的合成配方是6个<span style="color: tomato;">牛奶瓶</span>2个金锭和1个金苹果(有序合成)</p>
          <div class="recipe">
            $m: ./assets/imgs/mod_texture/milk_bottle.png;
            $g: ./assets/imgs/gold_ingot.png;
            $#: ./assets/imgs/Golden_Apple_JE2_BE2.png;
            result: ./assets/imgs/mod_texture/clear_harmful_potion.png;
            count: 6;
            (1,1): $m; (1,2): $m; (1,3): $m;
            (2,1): $g; (2,2): $#; (2,3): $g;
            (3,1): $m; (3,2): $m; (3,3): $m;
          </div>
        </div>
        <div class="section">
          <h1>负面效果免疫药水</h1>
          <p>正在开发, 敬请期待</p>
        </div>
        <footer>
          <div class="left">
            <p>&copy; 2024 kzhik, All rights reserved.</p>
          </div>
          <div class="right">
            
          </div>
        </footer>
      </div>
    </main>
    <script src="./resources/script/renderRecipes.js"></script>
    <script src="./resources/script/renderAntidoteMaterialsList.js"></script>
  </body>
</html>
