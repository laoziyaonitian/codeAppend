# codeAppend - 代码追加工具

### 功能清单
- [x] 支持追加不存在的sql字段代码（entity,mapper）
- [x] 支持lombok风格代码生成
- [x] 生成mybatis代码（entity,service,dao,mapper）
- [ ] 支持选择是否删除表名/字段前缀
- [ ] 支持get/set代码风格生成
- [ ] 配置中心

### 使用说明
#### append方式使用说明：
![append使用方法](https://github.com/laoziyaonitian/codeAppend/blob/master/file/append.gif)
> * append方式不会覆盖文件，只会追加不存在的字段
> * 默认删除表名前缀

#### override方式使用说明：
![override使用方法](https://github.com/laoziyaonitian/codeAppend/blob/master/file/override.gif)
> * override会生成代码文件，如果已存在会覆盖代码文件
> * 默认删除表名前缀
ps:不需要生成的文件可以不填写文件生成目录

#### sql code example:
```sql
/*
Navicat MySQL Data Transfer
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pre_test
-- ----------------------------
DROP TABLE IF EXISTS `pre_test`;
CREATE TABLE `pre_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `num_no` varchar(64) NOT NULL COMMENT '订单号',
  `fu_address` varchar(32) DEFAULT NULL COMMENT '详细地址',
  `longitude` varchar(16) DEFAULT NULL COMMENT '地址经度',
  `latitude` varchar(16) DEFAULT NULL COMMENT '地址纬度',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址表';

-- ----------------------------
-- Table structure for pre_test1
-- ----------------------------
DROP TABLE IF EXISTS `pre_test1`;
CREATE TABLE `pre_test2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` varchar(64) NOT NULL,
  `attach_type` varchar(32) NOT NULL COMMENT '附件类型',
  `store_id` varchar(128) NOT NULL COMMENT '存储id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `deleted` tinyint(4) NOT NULL COMMENT '是否删除(0-否、1-是）',
  PRIMARY KEY (`id`),
  KEY `idx_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';
```
#### 界面说明：
> * ![界面说明](https://github.com/laoziyaonitian/codeAppend/blob/master/file/%E7%95%8C%E9%9D%A2.png)

### release note:
> * v1.0.0
> * 输入sql，追加生成lombok风格的mybatis相关代码
> * 支持追加新字段（append）和全量覆盖（override）两种方式