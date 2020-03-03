package com.toolsfordevs.fast.core.processor

import java.io.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind
import javax.tools.FileObject
import javax.tools.StandardLocation

abstract class BaseProcessor<T:Annotation>(private val annotationClass: Class<T>, private val interfaceQualifiedName:String) : AbstractProcessor()
{
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    private val interfaces = arrayListOf<String>()

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean
    {
        try
        {
            return processImpl(annotations, roundEnv)
        }
        catch (e: Exception)
        {
            // We don't allow exceptions of any kind to propagate to the compiler
            val writer = StringWriter()
            e.printStackTrace(PrintWriter(writer))
            fatalError(writer.toString())
            return true
        }
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String>
    {
        return mutableSetOf(annotationClass.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion
    {
        return SourceVersion.RELEASE_8
    }

    override fun getSupportedOptions(): MutableSet<String>
    {
        return mutableSetOf(KAPT_KOTLIN_GENERATED_OPTION_NAME)
    }

    private fun processImpl(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean
    {
        if (roundEnv.processingOver())
            generateConfigFiles()
        else
            processAnnotations(annotations, roundEnv)

        return true
    }

    private fun processAnnotations(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment)
    {

        val elements = roundEnv.getElementsAnnotatedWith(annotationClass)

        for (e in elements)
        {
            val className = e.simpleName.toString()
            val pack = processingEnv.elementUtils.getPackageOf(e).toString()

            interfaces.add("$pack.$className")
        }
    }

    private fun generateConfigFiles()
    {
        val filer = processingEnv.filer
        val service = interfaceQualifiedName

        val resourceFile = "META-INF/services/$service"
        val file:FileObject

        if (interfaces.size > 0)
        {
            file = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile)
            val out = file.openOutputStream()

            try
            {
                write(out)
            }
            catch (e: Exception)
            {
                log("Resource file did not already exist. $file")
            }
            out.close()
        }
        else
        {
            try
            {
                file = filer.getResource(StandardLocation.CLASS_OUTPUT, "", resourceFile)
                file.delete()
            }
            catch (e : Exception) {}
        }
    }

    @Throws(IOException::class)
    private fun write(output: OutputStream)
    {
        val writer = BufferedWriter(OutputStreamWriter(output, "UTF-8"))
        for (plugin in interfaces)
        {
            writer.write(plugin)
            writer.newLine()
        }
        writer.flush()
    }

    private fun log(msg: String)
    {
        processingEnv.messager.printMessage(Kind.WARNING, msg)
    }

    private fun fatalError(msg: String)
    {
        processingEnv.messager.printMessage(Kind.ERROR, "FATAL ERROR: $msg")
    }
}