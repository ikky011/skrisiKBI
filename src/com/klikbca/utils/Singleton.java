package com.klikbca.utils;

public class Singleton {
		private static Singleton instance;

		public static Singleton Getinstance(){
			if (instance == null){
				instance = new Singleton();
				
			}
			return instance;
		}

		public String path;
		public String outdir;

		public Singleton() {
			path = null;
			outdir = null;
		}
		}

