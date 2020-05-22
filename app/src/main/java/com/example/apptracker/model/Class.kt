package com.example.apptracker.model

//Historial
data class Historial(var idUsuario:String ="", var nombre:String ="", var juego:String ="", var tracker:String ="")

//Warzone
data class Result(var data: Data)
data class Data(var platformInfo: PlatformInfo, var segments:List<Segments>)
data class PlatformInfo(var platformUserIdentifier: String, var avatarUrl:String)
data class Segments(var stats: Stats)
data class Stats(var kills: Kills, var deaths: Deaths, var kdRatio: KdRatio, var wins: Wins, var gamesPlayed: GamesPlayed, var timePlayed: TimePlayed, var level: Level)
data class Kills(var value: String)
data class Deaths(var value: String)
data class KdRatio(var value: String)
data class Wins(var value: String)
data class GamesPlayed(var value: String)
data class TimePlayed(var displayValue: String)
data class Level(var value: String, var metadata: MetadataLevel)
data class MetadataLevel(var iconUrl: String)

//Apex
data class ResultA(var data:DataA)
data class DataA(var platformInfo:PlatformInfoA,var metadata:MetadataA, var segments:List<SegmentsA>)
data class PlatformInfoA(var platformUserIdentifier: String, var avatarUrl:String)
data class MetadataA(var activeLegendName:String)
data class SegmentsA(var stats:StatsA, var metadata:MetadataNameA)
data class MetadataNameA(var name:String)
data class StatsA(var kills:KillsA, var level:LevelA, var rankScore:RankScoreA, var seasonWins:Season4WinsA)
data class KillsA(var value: String)
data class LevelA(var value: String)
data class RankScoreA(var metadata:MetadataRankA)
data class MetadataRankA(var iconUrl:String, var rankName:String)
data class Season4WinsA(var displayvalue:String)

//CSGO
data class ResultC(var data:DataC)
data class DataC(var platformInfo:PlatformInfoC, var segments:List<SegmentsC>)
data class PlatformInfoC(var platformUserHandle: String, var avatarUrl:String)
data class SegmentsC(var stats:StatsC)
data class StatsC(var timePlayed: TimePlayedC, var kills: KillsC, var kd:KdC, var shotsAccuracy:ShotsAccuracyC, var bombsPlanted:BombsPlantedC, var bombsDefused:BombsDefusedC, var mvp:MvpC, var wlPercentage:WlPercentageC, var headshotPct:HeadshotPctC)
data class TimePlayedC(var displayValue: String)
data class KillsC(var displayValue: String)
data class KdC(var displayValue: String)
data class ShotsAccuracyC(var displayValue: String)
data class BombsPlantedC(var displayValue: String)
data class BombsDefusedC(var displayValue: String)
data class MvpC(var value: String)
data class WlPercentageC(var displayValue: String)
data class HeadshotPctC(var displayValue: String)


