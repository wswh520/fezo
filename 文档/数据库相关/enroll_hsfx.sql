/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016-6-24 15:58:41                           */
/*==============================================================*/


drop index ID_uid on TB_Attachment;

drop table if exists TB_Attachment;

drop table if exists TB_File;

drop index ID_xm on TB_GraStuInfo;

drop index ID_uid on TB_GraStuInfo;

drop table if exists TB_GraStuInfo;

drop index ID_mid on TB_GraStuInfo_History;

drop table if exists TB_GraStuInfo_History;

drop index ID_time on TB_Notice;

drop table if exists TB_Notice;

drop index ID_ut on TB_Online;

drop table if exists TB_Online;

drop index ID_cardNo_cardType on TB_StuApply;

drop index ID_name on TB_StuApply;

drop index ID_uid on TB_StuApply;

drop table if exists TB_StuApply;

drop index ID_mid on TB_StuApply_History;

drop table if exists TB_StuApply_History;

drop index ID_stuId on TB_StuInfo;

drop table if exists TB_StuInfo;

drop index ID_stuId on TB_StuVaccine;

drop table if exists TB_StuVaccine;

drop table if exists TB_TimeConfig;

drop index ID_username_dtime on TB_User;

drop table if exists TB_User;

drop table if exists TB_Year;

drop index ID_nid on TRE_Notice_Attachment;

drop table if exists TRE_Notice_Attachment;

/*==============================================================*/
/* Table: TB_Attachment                                         */
/*==============================================================*/
create table TB_Attachment
(
   id                   int(11) not null auto_increment,
   oid                  int(11) not null comment '所属实体ID',
   otype                tinyint(2) not null comment '所属实体类型',
   uid                  int(11) not null comment '用户ID',
   hash                 char(32) not null comment '文件hash',
   name                 varchar(255) not null comment '文件名',
   primary key (id)
);

alter table TB_Attachment comment '附件表';

/*==============================================================*/
/* Index: ID_uid                                                */
/*==============================================================*/
create index ID_uid on TB_Attachment
(
   uid
);

/*==============================================================*/
/* Table: TB_File                                               */
/*==============================================================*/
create table TB_File
(
   hash                 char(32) not null comment '文件hash',
   size                 bigint(20) not null default 0 comment '文件大小',
   rc                   int(11) not null default 0 comment '被引用的次数',
   data                 mediumblob not null,
   primary key (hash)
);

alter table TB_File comment '文件信息表';

/*==============================================================*/
/* Table: TB_GraStuInfo                                         */
/*==============================================================*/
create table TB_GraStuInfo
(
   id                   int(11) not null auto_increment,
   uid                  int(11) not null comment '所属用户ID',
   year                 smallint(6) not null comment '年份',
   pinyin               varchar(255) not null comment '姓名全拼',
   status               tinyint(1) not null default 1 comment '状态',
   xbxh                 varchar(255) not null comment '校编学号',
   bj                   varchar(255) not null comment '班级',
   xm                   varchar(255) not null comment '学生姓名',
   xb                   tinyint(1) not null comment '性别',
   jg1                  varchar(255) not null comment '籍贯省',
   jg2                  varchar(255) not null comment '籍贯市',
   csd                  varchar(255) not null comment '出生地',
   mz                   varchar(255) not null comment '民族',
   hkxz                 varchar(255) not null comment '户口性质',
   sfzh                 varchar(255) not null comment '身份证号',
   dz                   tinyint(1) not null comment '独生子女',
   sl                   tinyint(1) not null comment '视力残疾',
   tl                   tinyint(1) not null comment '听力残疾',
   zl                   tinyint(1) not null comment '智力残疾',
   jzxz                 varchar(255) not null comment '居住性质',
   jtzz                 varchar(255) not null comment '居住地址',
   hkszd                varchar(255) not null comment '户口所在地',
   hkdz1                varchar(255) not null comment '户口地址',
   hkdz2                varchar(255) not null comment '户口地址',
   fuxm                 varchar(255) not null comment '父亲姓名',
   fudw                 varchar(255) not null comment '父亲工作单位',
   fudh                 varchar(255) not null comment '父亲电话',
   muxm                 varchar(255) not null comment '母亲姓名',
   mudw                 varchar(255) not null comment '母亲工作单位',
   mudh                 varchar(255) not null comment '母亲电话',
   sylb                 tinyint(1) not null comment '生源类别',
   xmN                  varchar(255) comment 'N结尾的为用户核对时所填写的信息',
   jg1N                 varchar(255),
   jg2N                 varchar(255),
   csdN                 varchar(255),
   mzN                  varchar(255),
   hkxzN                varchar(255),
   sfzhN                varchar(255),
   dzN                  tinyint(1),
   slN                  tinyint(1),
   tlN                  tinyint(1),
   zlN                  tinyint(1),
   jzxzN                varchar(255),
   jtzzN                varchar(255),
   hkszdN               varchar(255),
   hkdz1N               varchar(255),
   hkdz2N               varchar(255),
   fuxmN                varchar(255),
   fudwN                varchar(255),
   fudhN                varchar(255),
   muxmN                varchar(255),
   mudwN                varchar(255),
   mudhN                varchar(255),
   sylbN                tinyint(1),
   zw                   varchar(255) comment '任何职务',
   tc                   varchar(255) comment '特长',
   shs                  varchar(255) comment '三好生',
   jl                   varchar(255) comment '所获奖励',
   primary key (id)
);

alter table TB_GraStuInfo comment '毕业生信息登记表';

/*==============================================================*/
/* Index: ID_uid                                                */
/*==============================================================*/
create unique index ID_uid on TB_GraStuInfo
(
   uid
);

/*==============================================================*/
/* Index: ID_xm                                                 */
/*==============================================================*/
create index ID_xm on TB_GraStuInfo
(
   xm
);

/*==============================================================*/
/* Table: TB_GraStuInfo_History                                 */
/*==============================================================*/
create table TB_GraStuInfo_History
(
   id                   int(11) not null auto_increment,
   mid                  int(11) not null comment '毕业信息登记表ID',
   uid                  int(11) not null comment '操作人ID',
   data                 text not null comment '数据内容JSON格式',
   time                 bigint(20) not null comment '存档时间',
   primary key (id)
);

/*==============================================================*/
/* Index: ID_mid                                                */
/*==============================================================*/
create index ID_mid on TB_GraStuInfo_History
(
   mid
);

/*==============================================================*/
/* Table: TB_Notice                                             */
/*==============================================================*/
create table TB_Notice
(
   id                   int(11) not null auto_increment,
   title                varchar(255) not null,
   text                 text not null,
   time                 bigint(20) not null,
   uid                  int(11) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: ID_time                                               */
/*==============================================================*/
create index ID_time on TB_Notice
(
   time
);

/*==============================================================*/
/* Table: TB_Online                                             */
/*==============================================================*/
create table TB_Online
(
   id                   varchar(255) not null,
   uid                  int(11) not null,
   dt                   bigint(20) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: ID_ut                                                 */
/*==============================================================*/
create index ID_ut on TB_Online
(
   dt
);

/*==============================================================*/
/* Table: TB_StuApply                                           */
/*==============================================================*/
create table TB_StuApply
(
   id                   int(11) not null auto_increment,
   uid                  int(11) not null comment '所属用户ID',
   year                 smallint(6) not null comment '招生年份，取当前年，不需要用户输入',
   status               tinyint(1) not null default 1 comment '状态：未提交、初次提交、再次提交、审核通过、审核未通过',
   type                 tinyint(1) not null comment '类别：华师了弟、华师第三代、共建单位子弟',
   no                   int(11) not null comment '报名编号',
   name                 varchar(255) not null comment '学生姓名',
   pinyin               varchar(255),
   dateOfBirth          bigint(20) not null comment '出生日期（eg:20020101）',
   sex                  tinyint(1) not null comment '性别',
   nation               varchar(255) not null comment '民族',
   addressOfBirth       varchar(255) not null comment '出生地',
   birthplace           varchar(255) not null comment '籍贯',
   jkzt                 varchar(255) comment '健康状况',
   citizenship          varchar(255) not null comment '国籍',
   cardType             tinyint(1) not null comment '证件类型',
   gatqw                varchar(255) comment '港澳台侨外',
   cardNo               varchar(255) not null comment '证件号',
   reviewer             varchar(255) comment '审核人',
   dateOfReview         bigint(20) comment '审核日期',
   note                 varchar(255) comment '备注',
   other1               varchar(255) comment 'other开头的为其它信息，参照对应的word表格',
   other2               varchar(255),
   other3               varchar(255),
   other4               varchar(255),
   other5               varchar(255),
   other6               varchar(255),
   other7               varchar(255),
   other8               varchar(255),
   other9               varchar(255),
   other10              varchar(255),
   other11              varchar(255),
   other12              varchar(255),
   other13              varchar(255),
   other14              varchar(255),
   other15              varchar(255),
   other16              varchar(255),
   other17              varchar(255),
   other18              varchar(255),
   other19              varchar(255),
   other20              varchar(255),
   other21              varchar(255),
   other22              varchar(255),
   other23              varchar(255),
   other24              varchar(255),
   other25              varchar(255),
   other26              varchar(255),
   other27              varchar(255),
   other28              varchar(255),
   other29              varchar(255),
   other30              varchar(255),
   other31              varchar(255),
   other32              varchar(255),
   other33              varchar(255),
   other34              varchar(255),
   other35              varchar(255),
   other36              varchar(255),
   other37              varchar(255) comment '是否独生子女',
   other38              varchar(255) comment '是否上过幼儿园',
   other39              varchar(255) comment '是否进城务工人员随迁子女',
   other40              varchar(255) comment '残疾类型(有，无)',
   other41              varchar(255) comment '华师第三代（外）祖父、母 的校园一卡通号码',
   other42              varchar(255) comment '如果残疾，说明哪里残疾',
   message              varchar(255) comment '给家长留言',
   primary key (id)
);

alter table TB_StuApply comment '学生申请表（新生入学登记表）';

/*==============================================================*/
/* Index: ID_uid                                                */
/*==============================================================*/
create unique index ID_uid on TB_StuApply
(
   uid
);

/*==============================================================*/
/* Index: ID_name                                               */
/*==============================================================*/
create index ID_name on TB_StuApply
(
   name
);

/*==============================================================*/
/* Index: ID_cardNo_cardType                                    */
/*==============================================================*/
create unique index ID_cardNo_cardType on TB_StuApply
(
   cardNo,
   cardType
);

/*==============================================================*/
/* Table: TB_StuApply_History                                   */
/*==============================================================*/
create table TB_StuApply_History
(
   id                   int(11) not null auto_increment,
   mid                  int(11) not null comment '入学登记表ID',
   uid                  int(11) not null comment '操作人ID',
   data                 text not null comment '数据内容JSON格式',
   time                 bigint(20) not null comment '存档时间',
   primary key (id)
);

/*==============================================================*/
/* Index: ID_mid                                                */
/*==============================================================*/
create index ID_mid on TB_StuApply_History
(
   mid
);

/*==============================================================*/
/* Table: TB_StuInfo                                            */
/*==============================================================*/
create table TB_StuInfo
(
   id                   int(11) not null auto_increment,
   stuId                int(11) not null,
   status               tinyint(1) not null default 1,
   addressOfBirth       varchar(255),
   other1               varchar(255),
   other2               varchar(255),
   other3               varchar(255),
   other4               varchar(255),
   other5               varchar(255),
   other6               varchar(255),
   other7               varchar(255),
   other8               varchar(255),
   other9               varchar(255),
   other10              varchar(255),
   other11              varchar(255),
   other12              varchar(255),
   other13              varchar(255),
   other14              varchar(255),
   other15              varchar(255),
   other16              varchar(255),
   other17              varchar(255),
   other18              varchar(255),
   other19              varchar(255),
   other20              varchar(255),
   other21              varchar(255),
   other22              varchar(255),
   other23              varchar(255),
   other24              varchar(255),
   other25              varchar(255),
   other26              varchar(255),
   other27              varchar(255),
   other28              varchar(255),
   other29              varchar(255),
   other30              varchar(255),
   other31              varchar(255),
   other32              varchar(255),
   other33              varchar(255),
   other34              varchar(255),
   other35              varchar(255),
   other36              varchar(255),
   other37              varchar(255),
   other38              varchar(255),
   other39              varchar(255),
   other40              varchar(255),
   other41              varchar(255),
   other42              varchar(255),
   other43              varchar(255),
   other44              varchar(255),
   other45              varchar(255),
   other46              varchar(255),
   other47              varchar(255),
   other48              varchar(255),
   other49              varchar(255),
   other50              varchar(255),
   other51              varchar(255),
   other52              varchar(255),
   other53              varchar(255),
   other54              varchar(255),
   other55              varchar(255),
   other56              varchar(255),
   other57              varchar(255),
   other58              varchar(255),
   other59              varchar(255),
   primary key (id)
);

alter table TB_StuInfo comment '学生基本信息表（全国中小学学籍管理系统学生基本信息表）';

/*==============================================================*/
/* Index: ID_stuId                                              */
/*==============================================================*/
create unique index ID_stuId on TB_StuInfo
(
   stuId
);

/*==============================================================*/
/* Table: TB_StuVaccine                                         */
/*==============================================================*/
create table TB_StuVaccine
(
   id                   int(11) not null auto_increment,
   stuId                int(11) not null,
   status               tinyint(1) not null,
   other1               tinyint(1) not null,
   other2               tinyint(1) not null,
   other3               tinyint(1) not null,
   other4               tinyint(1) not null,
   other5               tinyint(1) not null,
   other6               tinyint(1) not null,
   other7               tinyint(1) not null,
   other8               tinyint(1) not null,
   other9               tinyint(1) not null,
   other10              tinyint(1) not null,
   other11              tinyint(1) not null,
   other12              tinyint(1) not null,
   other13              tinyint(1) not null,
   other14              tinyint(1) not null,
   other15              tinyint(1) not null,
   other16              tinyint(1) not null,
   other17              tinyint(1) not null,
   other18              tinyint(1) not null,
   other19              tinyint(1) not null,
   other20              tinyint(1) not null,
   other21              tinyint(1) not null,
   other22              tinyint(1) not null,
   other23              tinyint(1) not null,
   other24              tinyint(1) not null,
   primary key (id)
);

alter table TB_StuVaccine comment '入托、入学儿童预防接种证查验登记表';

/*==============================================================*/
/* Index: ID_stuId                                              */
/*==============================================================*/
create unique index ID_stuId on TB_StuVaccine
(
   stuId
);

/*==============================================================*/
/* Table: TB_TimeConfig                                         */
/*==============================================================*/
create table TB_TimeConfig
(
   type                 tinyint(1) not null,
   startTime            bigint(20) not null,
   endTime              bigint(20) not null,
   primary key (type)
);

alter table TB_TimeConfig comment '系统操作时间段控制表';

/*==============================================================*/
/* Table: TB_User                                               */
/*==============================================================*/
create table TB_User
(
   id                   int(11) not null auto_increment,
   username             varchar(255) not null,
   password             varchar(255) not null,
   name                 varchar(255) not null,
   type                 tinyint(1) not null,
   token                varchar(255),
   status               tinyint(1) not null default 1,
   bj                   varchar(255) comment '毕业班老师所在班级',
   year                 smallint(6) comment '毕业班老师对应的年份',
   dtime                bigint(20) not null default 0,
   primary key (id)
);

/*==============================================================*/
/* Index: ID_username_dtime                                     */
/*==============================================================*/
create unique index ID_username_dtime on TB_User
(
   username,
   dtime
);

/*==============================================================*/
/* Table: TB_Year                                               */
/*==============================================================*/
create table TB_Year
(
   year                 smallint(6) not null,
   typeA                smallint(6) not null default 0 comment '报名人数，报名编号依据',
   typeB                smallint(6) not null default 0,
   typeC                smallint(6) not null default 0,
   primary key (year)
);

alter table TB_Year comment '年份';

/*==============================================================*/
/* Table: TRE_Notice_Attachment                                 */
/*==============================================================*/
create table TRE_Notice_Attachment
(
   id                   int(11) not null auto_increment,
   nid                  int(11) not null,
   aid                  int(11) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: ID_nid                                                */
/*==============================================================*/
create index ID_nid on TRE_Notice_Attachment
(
   nid
);

alter table TB_Attachment add constraint FK_TB_File_TB_Attachment foreign key (hash)
      references TB_File (hash) on delete restrict on update restrict;

alter table TB_GraStuInfo add constraint FK_TB_User_TB_GraStuInfo foreign key (uid)
      references TB_User (id) on delete restrict on update restrict;

alter table TB_GraStuInfo_History add constraint FK_TB_GraStuInfo_TB_GraStuInfo_History foreign key (mid)
      references TB_GraStuInfo (id) on delete restrict on update restrict;

alter table TB_GraStuInfo_History add constraint FK_TB_User_TB_GraStuInfo_History foreign key (uid)
      references TB_User (id) on delete restrict on update restrict;

alter table TB_Notice add constraint FK_TB_User_TB_Notice foreign key (uid)
      references TB_User (id) on delete restrict on update restrict;

alter table TB_Online add constraint FK_TB_User_TB_Online foreign key (uid)
      references TB_User (id) on delete cascade on update restrict;

alter table TB_StuApply add constraint FK_TB_User_TB_StuApply foreign key (uid)
      references TB_User (id) on delete restrict on update restrict;

alter table TB_StuApply_History add constraint FK_TB_StuApply_TBStuApply_History foreign key (mid)
      references TB_StuApply (id) on delete restrict on update restrict;

alter table TB_StuApply_History add constraint FK_TB_User_TB_StuApply_History foreign key (uid)
      references TB_User (id) on delete restrict on update restrict;

alter table TB_StuInfo add constraint FK_TB_StuApply_TB_StuInfo foreign key (stuId)
      references TB_StuApply (id) on delete restrict on update restrict;

alter table TB_StuVaccine add constraint FK_TB_StuApply_TB_StuVaccine foreign key (stuId)
      references TB_StuApply (id) on delete restrict on update restrict;

alter table TRE_Notice_Attachment add constraint FK_TB_Attachment_TRE_Notice_Attachment foreign key (aid)
      references TB_Attachment (id) on delete restrict on update restrict;

alter table TRE_Notice_Attachment add constraint FK_TB_Notice_TRE_Notice_Attachment foreign key (nid)
      references TB_Notice (id) on delete restrict on update restrict;

