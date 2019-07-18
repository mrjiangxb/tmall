package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101000655419";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCq8t3ChL5mY8QdzlC7n0RwNT58oftmK9jiH0j+0uJ06ybM4xBO9PCqfscjtIknTa0/ahnC9T0lJn/pLBLFsvYQ5LXJwvjYpEC+D22QobzXmHkSliHdhh09J10n29kDQsrBmqTBRTpYLgIyD3XCw/4WXNCiSvNzzd4L3ewi8j90BiMWwZCu1QApScuoYd2i6cQL1FsdqPs9iUNBsvoD76TfhIqUFeVg7E1Bxau/bpZ3W1EpWaCZbkeyfHo8jrQ2TfXQg0OEzRMGifKeJugVX3taEx+RxFktodJNAQ6uB+HVPBwg2gz/4K05gTYlP5iCb7hwnGfonQ5O1xzYPCMBCe23AgMBAAECggEAbuEQTXbkOG1Faz/Cxa9MKKugWB6IfYC7MA+yvPXzxHoxtHsivNPKtgt9Bv+2ZjPPx23+P6Hpgwsxs//OPUUSrWprLu6zkXEYPS6slj8RyZUwEoomdRTkKiI6HfxPCfpsoJ65dlJ8wW02J1o3u5h8WezhtiJ/PgGtoGzVGOWfmpS+bjtNyWxXwqkN8AM8YG0kXSPI/WWsTyT1kRcWkGl7GJDBHuR6SMVJRq/9ApjZQwOd+Rl3nRFXr7YWzK5zHWBBcYSqKv7blxBBTxhyhBZ4Majo+PvldgrgKFZqxLDibrROBw179Tk9LOs1cSPBfMBivQ9Zoug/L0OR3W373xopwQKBgQDwjifLq/jsgFAfCZ8FNSzLGbLYuCssZRTr0zVsgu/RaMWH3hBzAvEHPixwJbBbCqxtTrlGombbUI9ZGDrhjw/piADixiFJHwbiEl7jMUPCgksYHYQ/0FSqxC6dRq3Bi8TO9rsJdb6HpE+KNkUuDXYmdOE+kHXxt5aRmgX/Lg5QWQKBgQC17KJek/A7jvQhTb2jxVfEcN2EBwUoNoRCrZnohHGg0LKR+VFk5L3CrYTUrJ499Gw0Qr6zM5vJXKoAMdu2o14kdWAf2XoBCKkA5EafztTM9PimbxcJI/dVhrXzTNTeKd47V5TFCO8J0Y9p/4trWF6IYxAVy0omQPJXoxl9VaHsjwKBgGIzhidtIwdZMlwZT2OO26Rl707tkCJleW70IAEWStSQEuNuUgTBlKfCR93YZPAloVJWKuic76RkdMCIVbEy9+n77XCmNXxmQCcWHMpemCzVh9ZmZlCuuYGk6OqnqdMzPbrHmn84I/m3qAjiMsekEzGL0U/7JUryCXOTDxOnN4yxAoGBAKDr/7hvXhog2pw2+x/JqVgvihKGby5iAt5EV5cQyL46vSpYgoIfwCdbe9rwEk21Tp1vyOYVKaGVoR7o+3Pz0Iupc+zYX45Yjb4nLr2fpCbkLqpyK8s/EEKMFZhwhv85w8V0NDmSLHff8d8OV9INahl0nz7YAvARlQymTh7Kx5/LAoGBANkdEmqAjZH1yK+1LlT6SxStdsbTtCRaOFbL26fFeg+xh5CxJEhpYKm0FwwLKa4ca89zqhVQu0vDTXW1gM6//lqv+pquoHSlGIP3Sqb9oh9NtOZrA/LhG3NUSRTha0uWVvQMKvWFplibAut7BXYOj5rZxrhMpp+8agGKKz9/JFfs";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwMi5SiC6AgKWnvSDB1oneAfqSQ/2u2vrDX3iowHxpUoD0244ItfwyElqzYXnnW3t1W6H8QTUQ456RXiIl7M/XFNp2d3nBrdH1iEsIqDe8Ht9tGRy2pz+y211AKN+EyJA8eEBNLNMGVjLh+sh8dKvhg13RQgNh76T1k2Z88Xz1LqEBkM/FqVxyfxcHPJ/APeLw0ech/qFcMCOZ8XMCoSaL+uHskaHBogsEDQC3o2bvbZEjia75HOUqZBi2HZGOMyzYvq//rTzmQ3801S0wyTgSAHrdL+OU09UtYLvAwv/FNOAvrY0UCRcIvuqqSlDl4AzJ+F/exLGewZpJrzKnuhxCwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://silence.free.idcfengye.com/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";//jsp可改为servlet

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://silence.free.idcfengye.com/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

