#!/usr/bin/env python
# -*- coding: latin-1 -*-
# -----------------------------------------------------------------------------
# 
#
# Version 1.0 
# Script python per l'esame di Configurazioni per l'IoT
# Studenti 
# Davide Basile e Raffaele Pico
# Professore
# Luigi Patrono
# Tutor
# Vincenzo Mighali
# Sript originale TagTool.py modoficato secondo necessità
# -----------------------------------------------------------------------------
from __future__ import print_function

import logging
log = logging.getLogger('main')

import os
import sys
import time
import string
import struct
import argparse
import subprocess
import hmac, hashlib

sys.path.insert(1, os.path.split(sys.path[0])[0])
from cli import CommandLineInterface

import nfc
import nfc.clf
import nfc.ndef

def parse_version(string):
    try: major_version, minor_version = map(int, string.split('.'))
    except ValueError, AttributeError:
        msg = "%r is not a version string, expecting <int>.<int>"
        raise argparse.ArgumentTypeError(msg % string)
    if major_version < 0 or major_version > 15:
        msg = "major version %r is out of range, expecting 0...15"
        raise argparse.ArgumentTypeError(msg % major_version)
    if minor_version < 0 or minor_version > 15:
        msg = "minor version %r is out of range, expecting 0...15"
        raise argparse.ArgumentTypeError(msg % minor_version)
    return major_version << 4 | minor_version

def parse_uint8(string):
    for base in (10, 16):
        try:
            value = int(string, base)
            if value >= 0 and value <= 0xff:
                return value
        except ValueError:
            pass
    else:
        msg = "%r can not be read as an 8-bit unsigned integer"
        raise argparse.ArgumentTypeError(msg % string)

def parse_uint16(string):
    for base in (10, 16):
        try:
            value = int(string, base)
            if value >= 0 and value <= 0xffff:
                return value
        except ValueError:
            pass
    else:
        msg = "%r can not be read as a 16-bit unsigned integer"
        raise argparse.ArgumentTypeError(msg % string)

def parse_uint24(string):
    for base in (10, 16):
        try:
            value = int(string, base)
            if value >= 0 and value <= 0xffffff:
                return value
        except ValueError:
            pass
    else:
        msg = "%r can not be read as a 24-bit unsigned integer"
        raise argparse.ArgumentTypeError(msg % string)

#
# command parser
#
def add_show_parser(parser):
    pass

class TagTool(CommandLineInterface):
    def __init__(self):
        parser = ArgumentParser(
            formatter_class=argparse.RawDescriptionHelpFormatter,
            description="")
        parser.add_argument(
            "-p", dest="authenticate", metavar="PASSWORD",
            help="unlock with password if supported")
        subparsers = parser.add_subparsers(
            title="commands", dest="command")
        add_show_parser(subparsers.add_parser(
                'show', help='pretty print ndef data'))
        self.rdwr_commands = {"show": self.show_tag,
                              }
    
        super(TagTool, self).__init__(
            parser, groups="rdwr card dbg clf")

    def on_rdwr_startup(self, targets):
        if self.options.command in self.rdwr_commands.keys():
            print("** waiting for a tag **", file=sys.stderr)
            return targets

    def on_rdwr_connect(self, tag):   
        self.rdwr_commands[self.options.command](tag)
        return self.options.wait or self.options.loop
    
    def on_card_startup(self, target):
        if self.options.command == "emulate":
            target = self.prepare_tag(target)
            print("** waiting for a reader **", file=sys.stderr)
            return target

    def on_card_connect(self, tag):
        log.info("tag activated")
        return self.emulate_tag_start(tag)

    def on_card_release(self, tag):
        log.info("tag released")
        self.emulate_tag_stop(tag)
        return True

    def show_tag(self, tag):
        print(tag)
        Id = str(tag) 

        subprocess.call(["java", "LocalServer", Id])
        if self.options.verbose:
            print("Memory Dump:")
            print('  ' + '\n  '.join(tag.dump()))

class ArgparseError(SystemExit):
    def __init__(self, prog, message):
        super(ArgparseError, self).__init__(2, prog, message)
    
    def __str__(self):
        return '{0}: {1}'.format(self.args[1], self.args[2])

class ArgumentParser(argparse.ArgumentParser):
    def error(self, message):
        raise ArgparseError(self.prog, message)

if __name__ == '__main__':
    while True:
        try:
            TagTool().run()
        except ArgparseError as e:
            prog = e.args[1].split()
        else:
            time.sleep(2)
