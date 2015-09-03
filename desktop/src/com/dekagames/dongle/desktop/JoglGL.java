package com.dekagames.dongle.desktop;

import com.dekagames.dongle.GLCommon;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.nio.*;

/**
 * Created by deka on 26.06.14.
 */
public class JoglGL implements GLCommon {
    private GL2 gl;

    public JoglGL(GL glcontext){
        gl = glcontext.getGL2();
    }


    @Override
    public void glAttachShader(int program, int shader) {
        gl.glAttachShader(program, shader);
    }

    @Override
    public void glBindAttribLocation(int program, int index, String name) {

    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        gl.glBindBuffer(target, buffer);
    }

    @Override
    public void glBindFramebuffer(int target, int framebuffer) {

    }

    @Override
    public void glBindRenderbuffer(int target, int renderbuffer) {

    }

    @Override
    public void glBlendColor(float red, float green, float blue, float alpha) {

    }

    @Override
    public void glBlendEquation(int mode) {

    }

    @Override
    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {

    }

    @Override
    public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {

    }

    @Override
    public void glBufferData(int target, int size, Buffer data, int usage) {
        gl.glBufferData(target, size, data, usage);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, Buffer data) {

    }

    @Override
    public int glCheckFramebufferStatus(int target) {
        return 0;
    }

    @Override
    public void glCompileShader(int shader) {
        gl.glCompileShader(shader);
    }

    @Override
    public int glCreateProgram() {
        return gl.glCreateProgram();
    }

    @Override
    public int glCreateShader(int type) {
        return gl.glCreateShader(type);
    }

    @Override
    public void glDeleteBuffers(int n, IntBuffer buffers) {
        gl.glDeleteBuffers(n, buffers);
    }

    @Override
    public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {

    }

    @Override
    public void glDeleteProgram(int program) {
        gl.glDeleteProgram(program);
    }

    @Override
    public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {

    }

    @Override
    public void glDeleteShader(int shader) {
        gl.glDeleteShader(shader);
    }

    @Override
    public void glDetachShader(int program, int shader) {
        gl.glDetachShader(program, shader);
    }

    @Override
    public void glDisableVertexAttribArray(int index) {

    }

    @Override
    public void glDrawElements(int mode, int count, int type, int indices) {

    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        gl.glEnableVertexAttribArray(index);
    }

    @Override
    public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {

    }

    @Override
    public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {

    }

    @Override
    public void glGenBuffers(int n, IntBuffer buffers) {
        gl.glGenBuffers(n, buffers);
    }

    @Override
    public void glGenerateMipmap(int target) {

    }

    @Override
    public void glGenFramebuffers(int n, IntBuffer framebuffers) {

    }

    @Override
    public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {

    }

    @Override
    public String glGetActiveAttrib(int program, int index, IntBuffer size, IntBuffer type) {
        return null;
    }

    @Override
    public String glGetActiveUniform(int program, int index, IntBuffer size, IntBuffer type) {
        return null;
    }

    @Override
    public void glGetAttachedShaders(int program, int maxcount, IntBuffer count, IntBuffer shaders) {

    }

    @Override
    public int glGetAttribLocation(int program, String name) {
        return gl.glGetAttribLocation(program, name);
    }

    @Override
    public void glGetBooleanv(int pname, Buffer params) {

    }

    @Override
    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {

    }

    @Override
    public void glGetFloatv(int pname, FloatBuffer params) {

    }

    @Override
    public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {

    }

    @Override
    public void glGetProgramiv(int program, int pname, IntBuffer params) {
        gl.glGetProgramiv(program, pname, params);
    }

    @Override
    public void glGetProgramiv(int program, int pname, int[] params, int offset) {
        gl.glGetProgramiv(program, pname, params, offset);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
        buffer.order(ByteOrder.nativeOrder());
        ByteBuffer tmp = ByteBuffer.allocateDirect(4);
        tmp.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = tmp.asIntBuffer();

        gl.glGetProgramInfoLog(program, 1024*10, intBuffer, buffer);
        int numBytes = intBuffer.get(0);
        byte[] bytes = new byte[numBytes];
        buffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {

    }

    @Override
    public void glGetShaderiv(int shader, int pname, IntBuffer params) {
        gl.glGetShaderiv(shader, pname, params);
    }

    @Override
    public void glGetShaderiv(int shader, int pname, int[] params, int offset) {
        gl.glGetShaderiv(shader, pname, params, offset);
    }

    @Override
    public String glGetShaderInfoLog(int shader) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
        buffer.order(ByteOrder.nativeOrder());
        ByteBuffer tmp = ByteBuffer.allocateDirect(4);
        tmp.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = tmp.asIntBuffer();

        gl.glGetShaderInfoLog(shader, 1024*10, intBuffer, buffer);
        int numBytes = intBuffer.get(0);
        byte[] bytes = new byte[numBytes];
        buffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {

    }

    @Override
    public void glGetShaderSource(int shader, int bufsize, IntBuffer length, byte source) {

    }

    @Override
    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {

    }

    @Override
    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {

    }

    @Override
    public void glGetUniformfv(int program, int location, FloatBuffer params) {

    }

    @Override
    public void glGetUniformiv(int program, int location, IntBuffer params) {

    }

    @Override
    public int glGetUniformLocation(int program, String name) {
        return gl.glGetUniformLocation(program, name);
    }

    @Override
    public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {

    }

    @Override
    public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {

    }

    @Override
    public boolean glIsBuffer(int buffer) {
        return false;
    }

    @Override
    public boolean glIsEnabled(int cap) {
        return false;
    }

    @Override
    public boolean glIsFramebuffer(int framebuffer) {
        return false;
    }

    @Override
    public boolean glIsProgram(int program) {
        return false;
    }

    @Override
    public boolean glIsRenderbuffer(int renderbuffer) {
        return false;
    }

    @Override
    public boolean glIsShader(int shader) {
        return false;
    }

    @Override
    public boolean glIsTexture(int texture) {
        return false;
    }

    @Override
    public void glLinkProgram(int program) {
        gl.glLinkProgram(program);
    }

    @Override
    public void glReleaseShaderCompiler() {

    }

    @Override
    public void glRenderbufferStorage(int target, int internalformat, int width, int height) {

    }

    @Override
    public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
        gl.glShaderBinary(n, shaders, binaryformat, binary, length);
    }

    @Override
    public void glShaderSource(int shader, String source) {
        String[] str = {source};
        IntBuffer len = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        len.put(source.length());
        gl.glShaderSource(shader, 1, str, (int[]) null, 0);
    }

    @Override
    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {

    }

    @Override
    public void glStencilMaskSeparate(int face, int mask) {

    }

    @Override
    public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {

    }

    @Override
    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
        gl.glTexParameterfv(target, pname, params);
    }

    @Override
    public void glTexParameteri(int target, int pname, int param) {
        gl.glTexParameteri(target, pname, param);
    }

    @Override
    public void glTexParameteriv(int target, int pname, IntBuffer params) {
        gl.glTexParameteriv(target, pname, params);
    }

    @Override
    public void glUniform1f(int location, float x) {
        gl.glUniform1f(location, x);
        int error = gl.glGetError();
    }

    @Override
    public void glUniform1fv(int location, int count, FloatBuffer v) {
        gl.glUniform1fv(location, count, v);
    }

    @Override
    public void glUniform1i(int location, int x) {
        gl.glUniform1i(location, x);
    }

    @Override
    public void glUniform1iv(int location, int count, IntBuffer v) {

    }

    @Override
    public void glUniform2f(int location, float x, float y) {
        gl.glUniform2f(location, x, y);
    }

    @Override
    public void glUniform2fv(int location, int count, FloatBuffer v) {

    }

    @Override
    public void glUniform2i(int location, int x, int y) {

    }

    @Override
    public void glUniform2iv(int location, int count, IntBuffer v) {

    }

    @Override
    public void glUniform3f(int location, float x, float y, float z) {

    }

    @Override
    public void glUniform3fv(int location, int count, FloatBuffer v) {

    }

    @Override
    public void glUniform3i(int location, int x, int y, int z) {

    }

    @Override
    public void glUniform3iv(int location, int count, IntBuffer v) {

    }

    @Override
    public void glUniform4f(int location, float x, float y, float z, float w) {

    }

    @Override
    public void glUniform4fv(int location, int count, FloatBuffer v) {

    }

    @Override
    public void glUniform4i(int location, int x, int y, int z, int w) {

    }

    @Override
    public void glUniform4iv(int location, int count, IntBuffer v) {

    }

    @Override
    public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {

    }

    @Override
    public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {

    }

    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] params, int offset) {
        gl.glUniformMatrix4fv(location, count, transpose, params, offset);
    }

    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
        gl.glUniformMatrix4fv(location, count, transpose, value);
    }

    @Override
    public void glUseProgram(int program) {
        gl.glUseProgram(program);
    }

    @Override
    public void glValidateProgram(int program) {
        gl.glValidateProgram(program);
    }

    @Override
    public void glVertexAttrib1f(int indx, float x) {
        gl.glVertexAttrib1f(indx, x);
    }

    @Override
    public void glVertexAttrib1fv(int indx, FloatBuffer values) {
        gl.glVertexAttrib1fv(indx, values);
    }

    @Override
    public void glVertexAttrib2f(int indx, float x, float y) {
        gl.glVertexAttrib2f(indx, x, y);
    }

    @Override
    public void glVertexAttrib2fv(int indx, FloatBuffer values) {
        gl.glVertexAttrib2fv(indx, values);
    }

    @Override
    public void glVertexAttrib3f(int indx, float x, float y, float z) {
        gl.glVertexAttrib3f(indx, x, y, z);
    }

    @Override
    public void glVertexAttrib3fv(int indx, FloatBuffer values) {
        gl.glVertexAttrib3fv(indx, values);
    }

    @Override
    public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
        gl.glVertexAttrib4f(indx, x, y, z, w);
    }

    @Override
    public void glVertexAttrib4fv(int indx, FloatBuffer values) {
        gl.glVertexAttrib4fv(indx, values);
    }

    @Override
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
        gl.glVertexAttribPointer(indx,size,type,normalized,stride,ptr);
    }

    @Override
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
        gl.glVertexAttribPointer(indx,size,type,normalized,stride,ptr);
    }

    @Override
    public void glClientActiveTexture(int texture) {

    }

    @Override
    public void glColor4f(float red, float green, float blue, float alpha) {

    }

    @Override
    public void glColorPointer(int size, int type, int stride, Buffer pointer) {
        gl.glColorPointer(size, type, stride, pointer);
    }

    @Override
    public void glDisableClientState(int array) {
        gl.glDisableClientState(array);
    }

    @Override
    public void glEnableClientState(int array) {
        gl.glEnableClientState(array);
    }

    @Override
    public void glFogf(int pname, float param) {

    }

    @Override
    public void glFogfv(int pname, float[] params, int offset) {

    }

    @Override
    public void glFogfv(int pname, FloatBuffer params) {

    }

    @Override
    public void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar) {
        gl.glFrustumf(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public void glGenTextures(int n, int[] textures, int offset) {
        gl.glGenTextures(n, textures, offset);
    }

    @Override
    public void glGetIntegerv(int pname, int[] params, int offset) {
        gl.glGetIntegerv(pname, params, offset);
    }

    @Override
    public void glDeleteTextures(int n, int[] textures, int offset) {
        gl.glDeleteTextures(n, textures, offset);
    }

    @Override
    public void glLightModelf(int pname, float param) {

    }

    @Override
    public void glLightModelfv(int pname, float[] params, int offset) {

    }

    @Override
    public void glLightModelfv(int pname, FloatBuffer params) {

    }

    @Override
    public void glLightf(int light, int pname, float param) {

    }

    @Override
    public void glLightfv(int light, int pname, float[] params, int offset) {

    }

    @Override
    public void glLightfv(int light, int pname, FloatBuffer params) {

    }

    @Override
    public void glLoadIdentity() {

    }

    @Override
    public void glLoadMatrixf(float[] m, int offset) {

    }

    @Override
    public void glLoadMatrixf(FloatBuffer m) {

    }

    @Override
    public void glLogicOp(int opcode) {

    }

    @Override
    public void glMaterialf(int face, int pname, float param) {

    }

    @Override
    public void glMaterialfv(int face, int pname, float[] params, int offset) {

    }

    @Override
    public void glMaterialfv(int face, int pname, FloatBuffer params) {

    }

    @Override
    public void glMatrixMode(int mode) {

    }

    @Override
    public void glMultMatrixf(float[] m, int offset) {

    }

    @Override
    public void glMultMatrixf(FloatBuffer m) {

    }

    @Override
    public void glMultiTexCoord4f(int target, float s, float t, float r, float q) {

    }

    @Override
    public void glNormal3f(float nx, float ny, float nz) {

    }

    @Override
    public void glNormalPointer(int type, int stride, Buffer pointer) {

    }

    @Override
    public void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar) {

    }

    @Override
    public void glPointSize(float size) {

    }

    @Override
    public void glPopMatrix() {

    }

    @Override
    public void glPushMatrix() {

    }

    @Override
    public void glRotatef(float angle, float x, float y, float z) {

    }

    @Override
    public void glSampleCoverage(float value, boolean invert) {

    }

    @Override
    public void glScalef(float x, float y, float z) {

    }

    @Override
    public void glShadeModel(int mode) {

    }

    @Override
    public void glTexCoordPointer(int size, int type, int stride, Buffer pointer) {

    }

    @Override
    public void glTexEnvf(int target, int pname, float param) {

    }

    @Override
    public void glTexEnvfv(int target, int pname, float[] params, int offset) {

    }

    @Override
    public void glTexEnvfv(int target, int pname, FloatBuffer params) {

    }

    @Override
    public void glTranslatef(float x, float y, float z) {

    }

    @Override
    public void glVertexPointer(int size, int type, int stride, Buffer pointer) {
        gl.glVertexPointer(size, type, stride, pointer);
    }

    @Override
    public void glActiveTexture(int texture) {
        gl.glActiveTexture(texture);
    }

    @Override
    public void glBindTexture(int target, int texture) {
        gl.glBindTexture(target, texture);
    }

    @Override
    public void glBlendFunc(int sfactor, int dfactor) {
        gl.glBlendFunc(sfactor, dfactor);
    }

    @Override
    public void glClear(int mask) {
        gl.glClear(mask);
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        gl.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glClearDepthf(float depth) {
        gl.glClearDepthf(depth);
    }

    @Override
    public void glClearStencil(int s) {

    }

    @Override
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {

    }

    @Override
    public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {

    }

    @Override
    public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {

    }

    @Override
    public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {

    }

    @Override
    public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {

    }

    @Override
    public void glCullFace(int mode) {

    }

    @Override
    public void glDeleteTextures(int n, IntBuffer textures) {
        gl.glDeleteTextures(n, textures);
    }

    @Override
    public void glDepthFunc(int func) {

    }

    @Override
    public void glDepthMask(boolean flag) {

    }

    @Override
    public void glDepthRangef(float zNear, float zFar) {

    }

    @Override
    public void glDisable(int cap) {
        gl.glDisable(cap);
    }

    @Override
    public void glDrawArrays(int mode, int first, int count) {
        gl.glDrawArrays(mode, first, count);
    }

    @Override
    public void glDrawElements(int mode, int count, int type, Buffer indices) {
        gl.glDrawElements(mode, count, type, indices);
    }

    @Override
    public void glEnable(int cap) {
        gl.glEnable(cap);
    }

    @Override
    public void glFinish() {
        gl.glFinish();
    }

    @Override
    public void glFlush() {
        gl.glFlush();
    }

    @Override
    public void glFrontFace(int mode) {

    }

    @Override
    public void glGenTextures(int n, IntBuffer textures) {
        gl.glGenTextures(n, textures);
    }

    @Override
    public int glGetError() {
        return gl.glGetError();
    }

    @Override
    public void glGetIntegerv(int pname, IntBuffer params) {
        gl.glGetIntegerv(pname, params);
    }

    @Override
    public String glGetString(int name) {
        return gl.glGetString(name);
    }

    @Override
    public void glHint(int target, int mode) {
        gl.glHint(target, mode);
    }

    @Override
    public void glLineWidth(float width) {

    }

    @Override
    public void glPixelStorei(int pname, int param) {

    }

    @Override
    public void glPolygonOffset(float factor, float units) {

    }

    @Override
    public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {

    }

    @Override
    public void glScissor(int x, int y, int width, int height) {
        gl.glScissor(x, y, width, height);
    }

    @Override
    public void glStencilFunc(int func, int ref, int mask) {

    }

    @Override
    public void glStencilMask(int mask) {

    }

    @Override
    public void glStencilOp(int fail, int zfail, int zpass) {

    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexParameterf(int target, int pname, float param) {
        gl.glTexParameterf(target, pname, param);
    }

    @Override
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
        gl.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        gl.glViewport(x, y, width, height);
    }
}
