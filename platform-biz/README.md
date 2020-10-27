
#对接传化MWS
  1.商品同步接口
  ```aidl
   接口地址: https://open.tf56.com/openGateway/oauthentry/UO.WMS/1/singleItemSynchronize	
   
   名称	类型	是否必须	示例值	描述
   thirdPartyId	STRING	是		第三方系统的会员id
   actionType	STRING	是		操作类型：add/update
   code	STRING	是		商品编号
   barcode	STRING	是		条码
   name	STRING	是		商品名称
   shortName	STRING	否		简称
   englishName	STRING	否		英文名
   goodsCategoryCode	STRING	是		商品类别编号
   goodsCategoryName	STRING	是		商品类别名称
   model	STRING	是		规格
   unit	STRING	是		基本单位个/件/盒/罐/台/条/包/米/瓶/袋/套/箱/托/桶/只/支/张/份/本/把/副/块/提/吨/克/千克/根/片/碗/立方厘米/立方分米/立方米/厘米/千米/千升/升/毫升
   entryPackUnit	STRING	否		默认入库包装单位个/件/盒/罐/台/条/包/米/瓶/袋/套/箱/托/桶/只/支/张/份/本/把/副/块/提/吨/克/千克/根/片/碗/立方厘米/立方分米/立方米/厘米/千米/千升/升/毫升
   entryPackQty	INT	否		默认入库包装系数
   unloadPackUnit	STRING	否		默认出库包装单位个/件/盒/罐/台/条/包/米/瓶/袋/套/箱/托/桶/只/支/张/份/本/把/副/块/提/吨/克/千克/根/片/碗/立方厘米/立方分米/立方米/厘米/千米/千升/升/毫升
   unloadPackQty	INT	否		默认出库包装系数
   brand	STRING	否		品牌
   origin	STRING	否		产地
   color	STRING	否		颜色
   goodsLong	INT	否		长单位：毫米
   goodsWidth	INT	否		宽单位：毫米
   goodsHigh	INT	否		高单位：毫米
   goodsWeight	INT	否		毛重单位：克
   cost	INT	否		成本单位：元
   price	FLOAT	是		售价单位：元
   taxUnitPrice	FLOAT	否		含税单价单位：元
   enabledBatch	STRING	否		是否启用批次管理1=启用;0=停用
   requirement	STRING	否		存储要求常温/冷藏/恒温
   shelfLife	INT	否		保质天数
   createMan	STRING	否		创建人
   updateMan	STRING	否		修改人
   remark	STRING	否		备注 
    
```
2. 客户信息同步接口
 ```aidl
        https://open.tf56.com/openGateway/oauthentry/UO.WMS/1/customerSynchronize	

```
 