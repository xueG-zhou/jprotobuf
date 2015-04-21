/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Baidu company (the "License");
 * you may not use this file except in compliance with the License.
 *
 */
package com.baidu.bjf.remoting.protobuf.idlgenerate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.IDLProxyObject;
import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import com.baidu.bjf.remoting.protobuf.ProtobufIDLProxy;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.enumeration.EnumAttrPOJO;
import com.baidu.bjf.remoting.protobuf.enumeration.EnumPOJOClass;

/**
 * 
 * Test class for enum idl proxy
 *
 * @author xiemalin
 * @since 1.4.0
 */
public class EnumIDLGeneratorTest {

    @Test
    public void testEnumIDLProxy() throws IOException {
        
        String idl = ProtobufIDLGenerator.getIDL(EnumPOJOClass.class);
        IDLProxyObject idlProxyObject = ProtobufIDLProxy.createSingle(idl);
        
        idlProxyObject.put("enumAttr", "STRING");
        
        byte[] bytes = idlProxyObject.encode();
        Codec<EnumPOJOClass> codec = ProtobufProxy.create(EnumPOJOClass.class);
        EnumPOJOClass enumPOJOClass = codec.decode(bytes);
        Assert.assertEquals(enumPOJOClass.enumAttr.value(), EnumAttrPOJO.STRING.value());
    }
    
    private Map<String, IDLProxyObject> initialFromProtofile(String fileName) throws IOException {
        
        
        InputStream fis = EnumIDLGeneratorTest.class.getResourceAsStream(fileName);
        
        Map<String, IDLProxyObject> map = ProtobufIDLProxy.create(fis);
        Assert.assertNotNull(map);
        Assert.assertEquals(23, map.size());
        
        return map;
    }
    
    @Ignore
    @Test
    public void testBestComplexIDLProxy() throws IOException {
        
        Map<String, IDLProxyObject> map = initialFromProtofile("si_product_biz.proto");
        
        boolean containsKey = map.containsKey("ProductTemplateResponse");
        Assert.assertTrue(containsKey);
        
        byte[] bytes;
        IDLProxyObject idlProxyObject;
        idlProxyObject = map.get("ProductTemplateResponse");
        
        idlProxyObject.put("product_template.property_product_mapping.usage", "TUWEN_ICON");
        idlProxyObject.put("product_template.property_product_mapping.no", 1001);
        idlProxyObject.put("product_template.property_product_mapping.type", "IMG");
        idlProxyObject.put("product_template.property_product_mapping.value", new byte[] {10, 20});
        idlProxyObject.put("product_template.property_product_mapping.editable", true);
        idlProxyObject.put("product_template.property_product_mapping.max_length", 10000);
        idlProxyObject.put("product_template.property_product_mapping.literal", new byte[] {10, 20});
        idlProxyObject.put("product_template.property_product_mapping.enable_url", false);
        idlProxyObject.put("product_template.property_product_mapping.name", "matthew".getBytes("utf-8"));
        
        idlProxyObject.put("head.result.status", 101);
        idlProxyObject.put("product_template.id", 1);
        idlProxyObject.put("product_template.name", "matthew".getBytes("utf-8"));
        
        idlProxyObject.put("product_template.type", "USER_DEFINE");
        idlProxyObject.put("product_template.thumbnail.width", 111);
        idlProxyObject.put("product_template.thumbnail.height", 112);
        idlProxyObject.put("product_template.thumbnail.url", "http://test.com");
        
        idlProxyObject.put("product_template.preview.width", 111);
        idlProxyObject.put("product_template.preview.height", 112);
        idlProxyObject.put("product_template.preview.url", "http://test.com");
        
        idlProxyObject.put("product_template.template_size.type", 2);
        idlProxyObject.put("product_template.template_size.width", 333);
        idlProxyObject.put("product_template.template_size.height", 444);
        
        
        bytes = idlProxyObject.encode();
        Assert.assertNotNull(bytes);
    }
    
    @Ignore
    @Test
    public void testComplexIDLProxy() throws IOException {
        Map<String, IDLProxyObject> map = initialFromProtofile("si_product_biz.proto");
        
        boolean containsKey = map.containsKey("ProductAuditRejectRequest");
        Assert.assertTrue(containsKey);
        
        byte[] bytes;
        IDLProxyObject idlProxyObject;
        
        idlProxyObject = map.get("ProductAuditRejectRequest");
        
        idlProxyObject.put("userid", 500);
        idlProxyObject.put("head.reserved", 100);
        
        
        bytes = idlProxyObject.encode();
        
        IDLProxyObject decodeProxyObject = idlProxyObject.decode(bytes);
        Assert.assertEquals(500, decodeProxyObject.get("userid"));
        Assert.assertEquals(100, decodeProxyObject.get("head.reserved"));
        
        
        idlProxyObject = map.get("ProductPropertyRequest");
        Assert.assertNotNull(idlProxyObject);
        
        idlProxyObject.put("userid", 200);
        idlProxyObject.put("head.request_type", "USER_DEFINED");
        idlProxyObject.put("head.appid", "DSP");
        
        
        bytes = idlProxyObject.encode();
        
        decodeProxyObject = idlProxyObject.decode(bytes);
        Assert.assertEquals(200, decodeProxyObject.get("userid"));
        Assert.assertEquals(decodeProxyObject.get("head.request_type") + "", "USER_DEFINED");
        Assert.assertEquals(decodeProxyObject.get("head.appid") + "", "DSP");
    }
}