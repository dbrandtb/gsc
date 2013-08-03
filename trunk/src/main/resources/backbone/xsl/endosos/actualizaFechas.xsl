<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <lista-de-valores-vO xsi:type="java:mx.com.aon.portal.model.BaseObjectVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam" />
            <xsl:element name="value">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="label">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_nmsuplem_o']/@value" />
            </xsl:element>
        </lista-de-valores-vO>
    </xsl:template>         
</xsl:stylesheet>       