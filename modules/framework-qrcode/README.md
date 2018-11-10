#1. 二维码生成

#1.1. 使用说明

##1.1.1. maven依赖

        <dependency>
            <groupId>com.evada</groupId>
            <artifactId>inno-qrcode</artifactId>
            <version>版本号</version>
        </dependency>
        
##1.1.2. 使用示例

         // 文本型二维码
         String notificationTemplate = "【任务下发通知】任务编号+任务名称，请@微信号或者任务负责人中文名@微信号或者任务负责人中文名，请来汇杰8楼指令管理处领取任务，谢谢配合！";
         String forceReceiveTemplate = "【任务催收通知】任务编号+任务名称，请尚未“复核通过”的人员：@微信号或者任务负责人中文名@微信号或者任务负责人中文名，请速来汇杰8楼指令管理处交回任务书并变更状态，谢谢配合！";
         QRCodeUtils.generate(notificationTemplate, new File("/Users/bluethinking/Downloads/a-njcb-template.png"));
         QRCodeUtils.generate(forceReceiveTemplate, new File("/Users/bluethinking/Downloads/b-njcb-template.png"));
        
         // url型二维码
         QRCodeUtils.generate("http://www.e-vada.com", new File("/Users/bluethinking/Downloads/evadaSite.png"));
        
         // 名片型二维码
         VCard xiongDa = new VCard("杨小雄")
         .setEmail("xiaoxiong.yang@e-vada.com")
         .setAddress("厦门观音山运营中心18号楼1305单元")
         .setTitle("扫地的")
         .setCompany("深圳英华达科技有限公司")
         .setPhoneNumber("13799270328")
         .setWebsite("www.e-vada.com/");
         QRCodeUtils.generate(xiongDa, new File("/Users/bluethinking/Downloads/xiongda-vcard.png"));