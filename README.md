
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">vasp-vue v0.0.1</h1>
<h4 align="center">基于SpringBoot和Vue的全域多级村容村貌大数据监管平台</h4>

## 平台简介

本项目是基于RuoYi-Vue的二次开发，平台主要致力于村容村貌监测，对农村地区房屋、道路等公共生活空间进行监测，致力于消除乱搭乱建、 乱堆乱放、乱贴乱画等影响村庄环境现象，保持乡村面貌整洁。 
* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。
* 特别鸣谢：[Ruoyi-Vue](https://github.com/yangzongzhuan/RuoYi-Vue)

## 内置功能

* 概要数据展示
    * 以村庄为单位的人居环境概括统计数据展示，展示内容包括：村庄的三维地图、常住人口、房屋（院落）数量、街道数量、学校、广场等公共空间的数量和位置。
    
* 村容村貌数据展示
    * 按辖区查询村容村貌监测数据，包括：监测到的乱搭乱建、乱堆乱放和乱贴乱画的位置。（不同的颜色分别表示乱搭乱建、乱堆乱放和乱贴乱画三种监测点位置）
    
* 村容村貌整改监管
    * 上级向下达整改通知、查看整改情况。
    * 下级查看整改通知、汇报整改结果。
    
* 监管数据来源
    * 提供监管数据的单位和无人机信息
    
    | 序号 | 隶属单位  | 负责人 |  电话 | 无人机型号   | 飞手 | 详情 |  备注 |
    |----|-------|-----|-----|---------|----|----|-----|
    | 1  | XXX公司 | XXX |     | UAV-XXX |    |    |     | 
    | 2  | XXX公司 | XXX |     | UAV-XXX |    |    |     | 
    | 3  | XXX公司 | XXX |     | UAV-XXX |    |    |     |
    
    * 无人机飞行记录
    
    | 序号 | 隶属单位  | 负责人 |  电话 | 无人机型号   | 飞手 | 详情 |  飞行轨迹 |
        |----|-------|-----|-----|---------|----|----|-----|
        | 1  | XXX公司 | XXX |     | UAV-XXX |    |    |     | 
        | 2  | XXX公司 | XXX |     | UAV-XXX |    |    |     | 
        | 3  | XXX公司 | XXX |     | UAV-XXX |    |    |     |
    * 固定监控摄像机
    
   | 序号 | 隶属单位  | 负责人 |  电话 | 摄像机型号   | 位置信息 | 备注 |  
        |----|-------|-----|-----|---------|----|----|
        | 1  | XXX公司 | XXX |     | UAV-XXX |    |    |     
        | 2  | XXX公司 | XXX |     | UAV-XXX |    |    |     
        | 3  | XXX公司 | XXX |     | UAV-XXX |    |    |     