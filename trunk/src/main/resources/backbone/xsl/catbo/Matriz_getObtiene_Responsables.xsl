<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.catbo.model.ResponsablesVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDMATRIZ">
		            <xsl:element name="cdmatriz">
		                <xsl:value-of select="@CDMATRIZ" />
		            </xsl:element>
		        </xsl:if>
		        
                <xsl:if test="@CDNIVATN">
                    <xsl:element name="cdnivatn">
                        <xsl:value-of select="@CDNIVATN" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSNIVATN">
                    <xsl:element name="dsnivatn">
                        <xsl:value-of select="@DSNIVATN" />
                    </xsl:element>
                </xsl:if>
                
                
                <xsl:if test="@CDROLMAT">
                    <xsl:element name="cdrolmat">
                        <xsl:value-of select="@CDROLMAT" />
                    </xsl:element>                
                </xsl:if>
                
                <xsl:if test="@DSROLMATRIZ">
                    <xsl:element name="dsrolmat">
                        <xsl:value-of select="@DSROLMATRIZ" />
                    </xsl:element>                
                </xsl:if>
                
                
                
                <xsl:if test="@CDUSUARIO">
                    <xsl:element name="cdusuario">
                        <xsl:value-of select="@CDUSUARIO" />
                    </xsl:element>                
                </xsl:if>
                
                <xsl:if test="@DSUSUARI">
                    <xsl:element name="dsusuari">
                        <xsl:value-of select="@DSUSUARI" />
                    </xsl:element>                
                </xsl:if>
                
                <xsl:if test="@EMAIL">
                    <xsl:element name="email">
                        <xsl:value-of select="@EMAIL" />
                    </xsl:element>                
                </xsl:if>
                
                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>                
                </xsl:if>
                
                <xsl:if test="@CDMODULO">
                    <xsl:element name="cdmodulo">
                        <xsl:value-of select="@CDMODULO" />
                    </xsl:element>                
                </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>





