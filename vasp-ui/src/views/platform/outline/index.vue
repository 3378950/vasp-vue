<template>
  <div class="map-box">
    <div id="cesiumContainer"></div>
  </div>

</template>


<script>
  export default {
    name: "",
    mounted() {
      this.show3DTile();
    }
    ,
    methods: {
      show3DTile() {
        var url = 'http://127.0.0.1:5500/3dtile/tileset.json';
        const viewer = new Cesium.Viewer("cesiumContainer", {
          terrainProvider: Cesium.createWorldTerrain(),
          animation: false,
          timeline: false,
          baseLayerPicker:false,  //右上角的图层选择按钮
          geocoder: false,
          homeButton: false,
          navigationHelpButton: false,
          sceneModePicker: false,
          fullscreenButton:false   //右下角的全屏按钮
        });

        viewer.cesiumWidget.creditContainer.style.display = "none";

        window.viewer = viewer;
        // eslint-disable-next-line no-unused-vars
        let tileset = viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
          url: url,
          // modelMatrix: Cesium.Matrix4.fromArray([1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1]),
          baseScreenSpaceError: 1024,
          //【重要】数值加大，能让最终成像变模糊
          skipScreenSpaceErrorFactor: 16,
          skipLevels: 1,
          immediatelyLoadDesiredLevelOfDetail: false,
          loadSiblings: false,
          cullWithChildrenBounds: true,
          skipLevelOfDetail: true, //开启跳级加载
          //这个参数默认是false，同等条件下，叶子节点会优先加载。但是Cesium的tile加载优先级有很多考虑条件，
          //这个只是其中之一，如果skipLevelOfDetail=false，这个参数几乎无意义。所以要配合skipLevelOfDetail=true来使用，
          //此时设置preferLeaves=true。这样我们就能最快的看见符合当前视觉精度的块，对于提升大数据以及网络环境不好的前提下有一点点改善意义。
          preferLeaves: true,
          //【重要】内存建议显存大小的50%左右，内存分配变小有利于倾斜摄影数据回收，提升性能体验
          maximumMemoryUsage: 1024
          //控制切片视角显示的数量，可调整性能
          // maximumScreenSpaceError: 2,//最大的屏幕空间误差
          // maximumNumberOfLoadedTiles: 100000, //最大加载瓦片个数
        }));
        window.viewer.flyTo(tileset)
        viewer.camera.setView({
          destination: Cesium.Cartesian3.fromDegrees(-117.16, 32.71, 15000.0)
        });
      }
    }
  };
</script>
<style scoped>
  .map-box {
    width: 100%;
    height: 1000px;
  }

  #cesiumContainer {
    width: 100%;
    height: 100%;
  }
</style>
