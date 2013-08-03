<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg-text">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.CatalogoLogicoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        
		        <xsl:if test="@TABLA">
		            <xsl:element name="cdtabla">
		                <xsl:value-of select="@TABLA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@REGION">
		            <xsl:element name="cdregion">
		                <xsl:value-of select="@REGION" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@IDIOMA">
		            <xsl:element name="cdidioma">
		                <xsl:value-of select="@IDIOMA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CODIGO">
		            <xsl:element name="codigo">
		                <xsl:value-of select="@CODIGO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DESCRIPCION">
		            <xsl:element name="descripcion">
		                <xsl:value-of select="@DESCRIPCION" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@descripcionlarga">
		            <xsl:element name="descripcion-larga">
		                <xsl:value-of select="@descripcionlarga" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@ETIQUETA">
		            <xsl:element name="etiqueta">
		                <xsl:value-of select="@ETIQUETA" />
		            </xsl:element>
		        </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>