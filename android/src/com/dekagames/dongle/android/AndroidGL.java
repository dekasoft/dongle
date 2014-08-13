package com.dekagames.dongle.android;

import android.annotation.TargetApi;
import android.opengl.GLES11;
import android.os.Build;
import com.dekagames.dongle.GLCommon;

import android.opengl.GLES20;
import android.opengl.GLES10;//javax.microedition.khronos.opengles.GL;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by deka on 18.06.14.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AndroidGL implements GLCommon {


    @Override
    public void glAttachShader(int program, int shader) {
        GLES20.glAttachShader(program, shader);
    }

    @Override
    public void glBindAttribLocation(int program, int index, String name) {
        GLES20.glBindAttribLocation(program, index, name);
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        GLES20.glBindBuffer(target, buffer);
    }

    @Override
    public void glBindFramebuffer(int target, int framebuffer) {
        GLES20.glBindFramebuffer(target, framebuffer);
    }

    @Override
    public void glBindRenderbuffer(int target, int renderbuffer) {
        GLES20.glBindRenderbuffer(target, renderbuffer);
    }

    @Override
    public void glBlendColor(float red, float green, float blue, float alpha) {
        GLES20.glBlendColor(red, green, blue, alpha);
    }

    @Override
    public void glBlendEquation(int mode) {
        GLES20.glBlendEquation(mode);
    }

    @Override
    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        GLES20.glBlendEquationSeparate(modeRGB, modeAlpha);
    }

    @Override
    public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        GLES20.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    @Override
    public void glBufferData(int target, int size, Buffer data, int usage) {
        GLES20.glBufferData(target, size, data, usage);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, Buffer data) {
        GLES20.glBufferSubData(target, offset, size, data);
    }

    @Override
    public int glCheckFramebufferStatus(int target) {
        return GLES20.glCheckFramebufferStatus(target);
    }

    @Override
    public void glCompileShader(int shader) {
        GLES20.glCompileShader(shader);
    }

    @Override
    public int glCreateProgram() {
        return GLES20.glCreateProgram();
    }

    @Override
    public int glCreateShader(int type) {
        return GLES20.glCreateShader(type);
    }

    @Override
    public void glDeleteBuffers(int n, IntBuffer buffers) {
        GLES20.glDeleteBuffers(n, buffers);
    }

    @Override
    public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
        GLES20.glDeleteFramebuffers(n, framebuffers);
    }

    @Override
    public void glDeleteProgram(int program) {
        GLES20.glDeleteProgram(program);
    }

    @Override
    public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
        GLES20.glDeleteRenderbuffers(n, renderbuffers);
    }

    @Override
    public void glDeleteShader(int shader) {
        GLES20.glDeleteShader(shader);
    }

    @Override
    public void glDetachShader(int program, int shader) {
        GLES20.glDetachShader(program, shader);
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        GLES20.glDisableVertexAttribArray(index);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void glDrawElements(int mode, int count, int type, int indices) {
        GLES20.glDrawElements(mode, count, type, indices);
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        GLES20.glEnableVertexAttribArray(index);
    }

    @Override
    public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
        GLES20.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
    }

    @Override
    public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        GLES20.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    @Override
    public void glGenBuffers(int n, IntBuffer buffers) {
        GLES20.glGenBuffers(n, buffers);
    }

    @Override
    public void glGenerateMipmap(int target) {
        GLES20.glGenerateMipmap(target);
    }

    @Override
    public void glGenFramebuffers(int n, IntBuffer framebuffers) {
        GLES20.glGenFramebuffers(n, framebuffers);
    }

    @Override
    public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
        GLES20.glGenRenderbuffers(n, renderbuffers);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public String glGetActiveAttrib(int program, int index, IntBuffer size, IntBuffer type) {
        return GLES20.glGetActiveAttrib(program, index, size, type);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public String glGetActiveUniform(int program, int index, IntBuffer size, IntBuffer type) {
        return GLES20.glGetActiveUniform(program, index, size, type);
    }

    @Override
    public void glGetAttachedShaders(int program, int maxcount, IntBuffer count, IntBuffer shaders) {
        GLES20.glGetAttachedShaders(program, maxcount, count, shaders);
    }

    @Override
    public int glGetAttribLocation(int program, String name) {
        return GLES20.glGetAttribLocation(program, name);
    }

    @Override
    public void glGetBooleanv(int pname, Buffer params) {
        GLES20.glGetBooleanv(pname, (IntBuffer)params);
    }

    @Override
    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
        GLES20.glGetBufferParameteriv(target, pname, params);
    }

    @Override
    public void glGetFloatv(int pname, FloatBuffer params) {
        GLES20.glGetFloatv(pname, params);
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
        GLES20.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
    }

    @Override
    public void glGetProgramiv(int program, int pname, IntBuffer params) {
        GLES20.glGetProgramiv(program, pname, params);
    }

    @Override
    public void glGetProgramiv(int program, int pname, int[] params, int offset) {
        GLES20.glGetProgramiv(program, pname, params, offset);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        return GLES20.glGetProgramInfoLog(program);
    }

    @Override
    public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        GLES20.glGetRenderbufferParameteriv(target, pname, params);
    }

    @Override
    public void glGetShaderiv(int shader, int pname, IntBuffer params) {
        GLES20.glGetShaderiv(shader, pname, params);
    }

    @Override
    public void glGetShaderiv(int shader, int pname, int[] params, int offset) {
        GLES20.glGetShaderiv(shader, pname, params, offset);
    }


    @Override
    public String glGetShaderInfoLog(int shader) {
        return GLES20.glGetShaderInfoLog(shader);
    }

    @Override
    public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
        GLES20.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
    }

    @Override
    public void glGetShaderSource(int shader, int bufsize, IntBuffer length, byte source) {
        GLES20.glGetShaderSource(shader, bufsize, length, source);
    }

    @Override
    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        GLES20.glGetTexParameterfv(target, pname, params);
    }

    @Override
    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        GLES20.glGetTexParameteriv(target, pname, params);
    }

    @Override
    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        GLES20.glGetUniformfv(program, location, params);
    }

    @Override
    public void glGetUniformiv(int program, int location, IntBuffer params) {
        GLES20.glGetUniformiv(program, location, params);
    }

    @Override
    public int glGetUniformLocation(int program, String name) {
        return GLES20.glGetUniformLocation(program, name);
    }

    @Override
    public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
        GLES20.glGetVertexAttribfv(index, pname, params);
    }

    @Override
    public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
        GLES20.glGetVertexAttribiv(index, pname, params);
    }

    @Override
    public boolean glIsBuffer(int buffer) {
        return GLES20.glIsBuffer(buffer);
    }

    @Override
    public boolean glIsEnabled(int cap) {
        return GLES20.glIsEnabled(cap);
    }

    @Override
    public boolean glIsFramebuffer(int framebuffer) {
        return GLES20.glIsFramebuffer(framebuffer);
    }

    @Override
    public boolean glIsProgram(int program) {
        return GLES20.glIsProgram(program);
    }

    @Override
    public boolean glIsRenderbuffer(int renderbuffer) {
        return GLES20.glIsRenderbuffer(renderbuffer);
    }

    @Override
    public boolean glIsShader(int shader) {
        return GLES20.glIsShader(shader);
    }

    @Override
    public boolean glIsTexture(int texture) {
        return GLES20.glIsTexture(texture);
    }

    @Override
    public void glLinkProgram(int program) {
        GLES20.glLinkProgram(program);
    }

    @Override
    public void glReleaseShaderCompiler() {
        GLES20.glReleaseShaderCompiler();
    }

    @Override
    public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
        GLES20.glRenderbufferStorage(target, internalformat, width, height);
    }

    @Override
    public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
        GLES20.glShaderBinary(n, shaders, binaryformat, binary, length);
    }

    @Override
    public void glShaderSource(int shader, String string) {
        GLES20.glShaderSource(shader, string);
    }

    @Override
    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        GLES20.glStencilFuncSeparate(face, func, ref, mask);
    }

    @Override
    public void glStencilMaskSeparate(int face, int mask) {
        GLES20.glStencilMaskSeparate(face, mask);
    }

    @Override
    public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
        GLES20.glStencilOpSeparate(face, fail, zfail, zpass);
    }

    @Override
    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
        GLES20.glTexParameterfv(target, pname, params);
    }

    @Override
    public void glTexParameteri(int target, int pname, int param) {
        GLES20.glTexParameteri(target, pname, param);
    }

    @Override
    public void glTexParameteriv(int target, int pname, IntBuffer params) {
        GLES20.glTexParameteriv(target, pname, params);
    }

    @Override
    public void glUniform1f(int location, float x) {
        GLES20.glUniform1f(location, x);
    }

    @Override
    public void glUniform1fv(int location, int count, FloatBuffer v) {
        GLES20.glUniform1fv(location, count, v);
    }

    @Override
    public void glUniform1i(int location, int x) {
        GLES20.glUniform1i(location, x);
    }

    @Override
    public void glUniform1iv(int location, int count, IntBuffer v) {
        GLES20.glUniform1iv(location, count, v);
    }

    @Override
    public void glUniform2f(int location, float x, float y) {
        GLES20.glUniform2f(location, x, y);
    }

    @Override
    public void glUniform2fv(int location, int count, FloatBuffer v) {
        GLES20.glUniform2fv(location, count, v);
    }

    @Override
    public void glUniform2i(int location, int x, int y) {
        GLES20.glUniform2i(location, x, y);
    }

    @Override
    public void glUniform2iv(int location, int count, IntBuffer v) {
        GLES20.glUniform2iv(location, count, v);
    }

    @Override
    public void glUniform3f(int location, float x, float y, float z) {
        GLES20.glUniform3f(location, x, y, z);
    }

    @Override
    public void glUniform3fv(int location, int count, FloatBuffer v) {
        GLES20.glUniform3fv(location, count, v);
    }

    @Override
    public void glUniform3i(int location, int x, int y, int z) {
        GLES20.glUniform3i(location, x, y, z);
    }

    @Override
    public void glUniform3iv(int location, int count, IntBuffer v) {
        GLES20.glUniform3iv(location, count, v);
    }

    @Override
    public void glUniform4f(int location, float x, float y, float z, float w) {
        GLES20.glUniform4f(location, x, y, z, w);
    }

    @Override
    public void glUniform4fv(int location, int count, FloatBuffer v) {
        GLES20.glUniform4fv(location, count, v);
    }

    @Override
    public void glUniform4i(int location, int x, int y, int z, int w) {
        GLES20.glUniform4i(location, x, y, z, w);
    }

    @Override
    public void glUniform4iv(int location, int count, IntBuffer v) {
        GLES20.glUniform4iv(location, count, v);
    }

    @Override
    public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES20.glUniformMatrix2fv(location, count, transpose, value);
    }

    @Override
    public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES20.glUniformMatrix3fv(location, count, transpose, value);
    }

    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] params, int offset) {
        GLES20.glUniformMatrix4fv(location, count, transpose, params, offset);
    }


    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES20.glUniformMatrix4fv(location, count, transpose, value);
    }


    @Override
    public void glUseProgram(int program) {
        GLES20.glUseProgram(program);
    }

    @Override
    public void glValidateProgram(int program) {
        GLES20.glValidateProgram(program);
    }

    @Override
    public void glVertexAttrib1f(int indx, float x) {
        GLES20.glVertexAttrib1f(indx, x);
    }

    @Override
    public void glVertexAttrib1fv(int indx, FloatBuffer values) {
        GLES20.glVertexAttrib1fv(indx, values);
    }

    @Override
    public void glVertexAttrib2f(int indx, float x, float y) {
        GLES20.glVertexAttrib2f(indx, x, y);
    }

    @Override
    public void glVertexAttrib2fv(int indx, FloatBuffer values) {
        GLES20.glVertexAttrib2fv(indx, values);
    }

    @Override
    public void glVertexAttrib3f(int indx, float x, float y, float z) {
        GLES20.glVertexAttrib3f(indx, x, y, z);
    }

    @Override
    public void glVertexAttrib3fv(int indx, FloatBuffer values) {
        GLES20.glVertexAttrib3fv(indx, values);
    }

    @Override
    public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
        GLES20.glVertexAttrib4f(indx, x, y, z, w);
    }

    @Override
    public void glVertexAttrib4fv(int indx, FloatBuffer values) {
        GLES20.glVertexAttrib4fv(indx, values);
    }

    @Override
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
        GLES20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
        GLES20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    @Override
    public void glClientActiveTexture(int texture) {
        GLES10.glClientActiveTexture(texture);
    }

    @Override
    public void glColor4f(float red, float green, float blue, float alpha) {
        GLES10.glColor4f(red, green, blue, alpha);
    }

    @Override
    public void glColorPointer(int size, int type, int stride, Buffer pointer) {
        GLES10.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glDisableClientState(int array) {
        GLES10.glDisableClientState(array);
    }

    @Override
    public void glEnableClientState(int array) {
        GLES10.glEnableClientState(array);
    }

    @Override
    public void glFogf(int pname, float param) {
        GLES10.glFogf(pname, param);
    }

    @Override
    public void glFogfv(int pname, float[] params, int offset) {
        GLES10.glFogfv(pname, params, offset);
    }

    @Override
    public void glFogfv(int pname, FloatBuffer params) {
        GLES10.glFogfv(pname, params);
    }

    @Override
    public void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar) {
        GLES10.glFrustumf(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public void glGenTextures(int n, int[] textures, int offset) {
        GLES20.glGenTextures(n, textures, offset);
    }

    @Override
    public void glGetIntegerv(int pname, int[] params, int offset) {
        GLES20.glGetIntegerv(pname, params, offset);
    }

    @Override
    public void glLightModelf(int pname, float param) {
        GLES10.glLightModelf(pname, param);
    }

    @Override
    public void glLightModelfv(int pname, float[] params, int offset) {
        GLES10.glLightModelfv(pname, params, offset);
    }

    @Override
    public void glLightModelfv(int pname, FloatBuffer params) {
        GLES10.glLightModelfv(pname, params);
    }

    @Override
    public void glLightf(int light, int pname, float param) {
        GLES10.glLightf(light, pname, param);
    }

    @Override
    public void glLightfv(int light, int pname, float[] params, int offset) {
        GLES10.glLightfv(light, pname, params, offset);
    }

    @Override
    public void glLightfv(int light, int pname, FloatBuffer params) {
        GLES10.glLightfv(light, pname, params);
    }

    @Override
    public void glLoadIdentity() {
        GLES10.glLoadIdentity();
    }

    @Override
    public void glLoadMatrixf(float[] m, int offset) {
        GLES10.glLoadMatrixf(m, offset);
    }

    @Override
    public void glLoadMatrixf(FloatBuffer m) {
        GLES10.glLoadMatrixf(m);
    }

    @Override
    public void glLogicOp(int opcode) {
        GLES10.glLogicOp(opcode);
    }

    @Override
    public void glMaterialf(int face, int pname, float param) {
        GLES10.glMaterialf(face, pname, param);
    }

    @Override
    public void glMaterialfv(int face, int pname, float[] params, int offset) {
        GLES10.glMaterialfv(face, pname, params, offset);
    }

    @Override
    public void glMaterialfv(int face, int pname, FloatBuffer params) {
        GLES10.glMaterialfv(face, pname, params);
    }

    @Override
    public void glMatrixMode(int mode) {
        GLES10.glMatrixMode(mode);
    }

    @Override
    public void glMultMatrixf(float[] m, int offset) {
        GLES10.glMultMatrixf(m, offset);
    }

    @Override
    public void glMultMatrixf(FloatBuffer m) {
        GLES10.glMultMatrixf(m);
    }

    @Override
    public void glMultiTexCoord4f(int target, float s, float t, float r, float q) {
        GLES10.glMultiTexCoord4f(target, s, t, r, q);
    }

    @Override
    public void glNormal3f(float nx, float ny, float nz) {
        GLES10.glNormal3f(nx, ny, nz);
    }

    @Override
    public void glNormalPointer(int type, int stride, Buffer pointer) {
        GLES10.glNormalPointer(type, stride, pointer);
    }

    @Override
    public void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar) {
        GLES10.glOrthof(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public void glPointSize(float size) {
        GLES10.glPointSize(size);
    }

    @Override
    public void glPopMatrix() {
        GLES10.glPopMatrix();
    }

    @Override
    public void glPushMatrix() {
        GLES10.glPushMatrix();
    }

    @Override
    public void glRotatef(float angle, float x, float y, float z) {
        GLES10.glRotatef(angle, x, y, z);
    }

    @Override
    public void glSampleCoverage(float value, boolean invert) {
        GLES20.glSampleCoverage(value, invert);
    }

    @Override
    public void glScalef(float x, float y, float z) {
        GLES10.glScalef(x, y, z);
    }

    @Override
    public void glShadeModel(int mode) {
        GLES10.glShadeModel(mode);
    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, Buffer pointer) {
        GLES10.glTexCoordPointer(size, type, stride, pointer);
    }

    @Override
    public void glTexEnvf(int target, int pname, float param) {
        GLES10.glTexEnvf(target, pname, param);
    }

    @Override
    public void glTexEnvfv(int target, int pname, float[] params, int offset) {
        GLES10.glTexEnvfv(target, pname, params, offset);
    }

    @Override
    public void glTexEnvfv(int target, int pname, FloatBuffer params) {
        GLES10.glTexEnvfv(target, pname, params);
    }

    @Override
    public void glTranslatef(float x, float y, float z) {
        GLES10.glTranslatef(x, y, z);
    }

    @Override
    public void glVertexPointer(int size, int type, int stride, Buffer pointer) {
        GLES10.glVertexPointer(size, type, stride, pointer);
    }


    @Override
    public void glActiveTexture(int texture) {
        GLES20.glActiveTexture(texture);
    }

    @Override
    public void glBindTexture(int target, int texture) {
        GLES20.glBindTexture(target, texture);
    }

    @Override
    public void glBlendFunc(int sfactor, int dfactor) {
        GLES20.glBlendFunc(sfactor, dfactor);
    }

    @Override
    public void glClear(int mask) {
        GLES20.glClear(mask);
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GLES20.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glClearDepthf(float depth) {
        GLES20.glClearDepthf(depth);
    }

    @Override
    public void glClearStencil(int s) {
        GLES20.glClearStencil(s);
    }

    @Override
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        GLES20.glColorMask(red, green, blue, alpha);
    }

    @Override
    public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
        GLES20.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
    }

    @Override
    public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
        GLES20.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
    }

    @Override
    public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
        GLES20.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
    }

    @Override
    public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        GLES20.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    @Override
    public final void glCullFace(int mode) {
        GLES20.glCullFace(mode);
    }

    @Override
    public final void glDeleteTextures(int n, IntBuffer textures) {
        GLES20.glDeleteTextures(n, textures);
    }


    @Override
    public final void glDeleteTextures(int n, int[] textures, int offset){
        GLES20.glDeleteTextures(n, textures, offset);
    }

    @Override
    public void glDepthFunc(int func) {
        GLES20.glDepthFunc(func);
    }

    @Override
    public void glDepthMask(boolean flag) {
        GLES20.glDepthMask(flag);
    }

    @Override
    public void glDepthRangef(float zNear, float zFar) {
        GLES20.glDepthRangef(zNear, zFar);
    }

    @Override
    public void glDisable(int cap) {
        GLES20.glDisable(cap);
    }

    @Override
    public void glDrawArrays(int mode, int first, int count) {
        GLES20.glDrawArrays(mode, first, count);
    }

    @Override
    public void glDrawElements(int mode, int count, int type, Buffer indices) {
        GLES20.glDrawElements(mode, count, type, indices);
    }

    @Override
    public void glEnable(int cap) {
        GLES20.glEnable(cap);
    }

    @Override
    public void glFinish() {
        GLES20.glFinish();
    }

    @Override
    public void glFlush() {
        GLES20.glFlush();
    }

    @Override
    public void glFrontFace(int mode) {
        GLES20.glFrontFace(mode);
    }

    @Override
    public void glGenTextures(int n, IntBuffer textures) {
        GLES20.glGenTextures(n, textures);
    }

    @Override
    public int glGetError() {
        return GLES20.glGetError();
    }

    @Override
    public void glGetIntegerv(int pname, IntBuffer params) {
        GLES20.glGetIntegerv(pname, params);
    }

    @Override
    public String glGetString(int name) {
        return GLES20.glGetString(name);
    }

    @Override
    public void glHint(int target, int mode) {
        GLES20.glHint(target, mode);
    }

    @Override
    public void glLineWidth(float width) {
        GLES20.glLineWidth(width);
    }

    @Override
    public void glPixelStorei(int pname, int param) {
        GLES20.glPixelStorei(pname, param);
    }

    @Override
    public void glPolygonOffset(float factor, float units) {
        GLES20.glPolygonOffset(factor, units);
    }

    @Override
    public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
        GLES20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glScissor(int x, int y, int width, int height) {
        GLES20.glScissor(x, y, width, height);
    }

    @Override
    public void glStencilFunc(int func, int ref, int mask) {
        GLES20.glStencilFunc(func, ref, mask);
    }

    @Override
    public void glStencilMask(int mask) {
        GLES20.glStencilMask(mask);
    }

    @Override
    public void glStencilOp(int fail, int zfail, int zpass) {
        GLES20.glStencilOp(fail, zfail, zpass);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        GLES20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexParameterf(int target, int pname, float param) {
        GLES20.glTexParameterf(target, pname, param);
    }

    @Override
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
        GLES20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        GLES20.glViewport(x, y, width, height);
    }
}
