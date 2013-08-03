<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S' ">
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
                <xsl:if test="@CDTIPSIT">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdtipsit
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDTIPSIT"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDATRIBU">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdatribu
                            </key>
                            <value xsi:type="java:java.lang.Integer">
                                <xsl:value-of select="@CDATRIBU"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWFORMAT">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                swformat
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWFORMAT"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@NMLMAX">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                nmlmax
                            </key>
                            <value xsi:type="java:java.lang.Integer">
                                <xsl:value-of select="@NMLMAX"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@NMLMIN">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                nmlmin
                            </key>
                            <value xsi:type="java:java.lang.Integer">
                                <xsl:value-of select="@NMLMIN"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                swobliga
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWOBLIGA"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@DSATRIBU">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                dsatribu
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@DSATRIBU"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@OTTABVAL">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                ottabval
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@OTTABVAL"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWPRODUC">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                swproduc
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWPRODUC"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWSUPLEM">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                swsuplem
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWSUPLEM"/>
                            </value>
                        </values>
                    </xsl:if>
            
            </cursor>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>